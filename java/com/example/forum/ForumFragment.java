package com.example.forum;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.forum.databinding.FragmentForumBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ForumFragment extends Fragment {
    private FragmentForumBinding binding;
    private TopicListAdapter adapter;
    private String userId;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser;
    Map<String, Object> data;
    private String userRole;
    private ListView listview;
    Map<String, Object> topicData;
    Topic selectedTopic;
    private LocalDate currentDateNow;
    DateTimeFormatter formatter;
    private String formattedDate;
    private String documentId;
    private String title;

    public interface FragmentForumListeners{
        public void switchToTopic(String title);
    }
    FragmentForumListeners listeners;
    public interface FragmentTopicDescriptionListeners{
        public void switchToTopic(String title);
    }
    FragmentTopicDescriptionListeners listeners2;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listeners = (ForumFragment.FragmentForumListeners) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentForumListeners");
        }
        try {
            listeners2 = (FragmentTopicDescriptionListeners) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement FragmentLoginListeners");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumBinding.inflate(inflater, container, false);
        listview = binding.topiclist;

        binding.deletestopic.setEnabled(false);

        currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }
        initUI();

        fetchTopicsFromFirestore(); // Запускаем загрузку тем из Firestore
        return binding.getRoot();
    }
    private void initUI() {
        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            data = document.getData();
                            if (data.containsKey("role")) {
                                userRole = (String) data.get("role");
                                if ("admin".equals(userRole)) {
                                    binding.deletestopic.setEnabled(true);
                                }
                            }
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                });
        binding.deletestopic.setEnabled(false);
        // Настройка слушателей кликов
        setupClickListeners();
    }
    private void setupClickListeners() {
        binding.createtopic.setOnClickListener(v -> createTopic());
        binding.deletestopic.setOnClickListener(v -> deleteTopic());
        binding.toProfile.setOnClickListener(v -> toProfile());
        listview.setOnItemClickListener(this::onListItemClicked);
    }

    private void toProfile() {
        Intent profile = new Intent(getActivity(), profile.class);
        startActivity(profile);
    }

    private void createTopic() {
        topicData = new HashMap<>();
        topicData.put("name", "Hello");
        currentDateNow = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        formattedDate = currentDateNow.format(formatter);
        topicData.put("title", "Hello");
        topicData.put("description", "Создание примерного темы");
        topicData.put("date", formattedDate);
        topicData.put("username", "Oleg");
        topicData.put("fullDescription", "Создание примерного темы для форума");
        // Добавляем данные в коллекцию "topics"
        db.collection("topics").document("Hello")
                .set(topicData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("CreateTopic", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("CreateTopic", "Error writing document", e);
                    }
                });
    }
    private void deleteTopic() {
        db.collection("topics").document("HelloWorld")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DeleteTopic", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DeleteTopic", "Error deleting document", e);
                    }
                });
    }
    private void onListItemClicked(AdapterView<?> parent, View view, int position, long id) {
        selectedTopic = (Topic) parent.getItemAtPosition(position);
        title = selectedTopic.getTitle();
        listeners2.switchToTopic(title);
    }
    private void fetchTopicsFromFirestore() {
        db.collection("topics")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Topic> topicsList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Topic topic = new Topic(
                                    document.getString("title"),
                                    document.getString("description"),
                                    document.getString("date"),
                                    document.getString("username")
                            );
                            topicsList.add(topic);
                        }
                        adapter = new TopicListAdapter(getContext(), topicsList);
                        listview.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Error getting documents.", e);
                    }
                });
    }
}