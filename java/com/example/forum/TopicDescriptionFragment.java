package com.example.forum;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.forum.databinding.TopicdescriptionFragmentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class TopicDescriptionFragment extends Fragment {
    private TopicdescriptionFragmentBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String Title;
    private String fullDescription;
    private String username;
    private String date;

    public interface FragmentTopicDescriptionListeners{
        public void switchToForum();
    }
    TopicDescriptionFragment.FragmentTopicDescriptionListeners listeners;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listeners = (TopicDescriptionFragment.FragmentTopicDescriptionListeners) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement FragmentLoginListeners");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TopicdescriptionFragmentBinding.inflate(getLayoutInflater(), container, false);

        Bundle args = getArguments();
        if (args!= null) {
            Title = args.getString("title"); // Получаем title из Bundle
        }
        description();
        binding.back.setOnClickListener(v-> {
            listeners.switchToForum();
        });
        return binding.getRoot();
    }
    public void description(){
        db.collection("topics").document(Title)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Title = documentSnapshot.getString("title");
                            fullDescription = documentSnapshot.getString("fullDescription");
                            username = documentSnapshot.getString("username");
                            date = documentSnapshot.getString("date");
                            binding.TitleTextView.setText(Title);
                            binding.FullDescriptionTextView.setText(fullDescription);
                            binding.UsernameTextView.setText(username);
                            binding.Date.setText(date);
                        } else {
                            Log.d("TAG", "Document does not exist");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Ошибка при получении данных", e);
                    }
                });
    }
}