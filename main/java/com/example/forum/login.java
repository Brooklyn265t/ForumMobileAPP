package com.example.forum;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.forum.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends Fragment {
    private FragmentLoginBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface FragmentLoginListeners{
        public void switchToRegister();
    }

    FragmentLoginListeners listeners;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listeners = (FragmentLoginListeners) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement FragmentLoginListeners");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextTextEmailAddress.getText().toString();
                String password = binding.editTextTextPassword.getText().toString();
                if (!TextUtils.isEmpty(email) &&!TextUtils.isEmpty(password)) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = auth.getCurrentUser();
                                    if (currentUser!= null) {
                                        String userId = currentUser.getUid();
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        db.collection("users").document(userId)
                                                .get()
                                                .addOnCompleteListener(userTask -> {
                                                    if (userTask.isSuccessful()) {
                                                        DocumentSnapshot userDoc = userTask.getResult();
                                                        if (userDoc.exists()) {
                                                            // Данные пользователя найдены
                                                            String userEmail = userDoc.getString("email");
                                                            String userLogin = userDoc.getString("login");
                                                            String userRole = userDoc.getString("role");

                                                            // Передача данных в Activity
                                                            Intent intent = new Intent(getActivity(), profile.class);
                                                            intent.putExtra("userEmail", userEmail);
                                                            intent.putExtra("userLogin", userLogin);
                                                            intent.putExtra("userRole", userRole);
                                                            startActivity(intent);
                                                        } else {
                                                            // Документ пользователя не найден
                                                            Log.d("FirestoreUserData", "No such document");
                                                        }
                                                    } else {
                                                        // Ошибка при получении данных пользователя
                                                        Log.d("FirestoreUserData", "get failed with ", userTask.getException());
                                                    }
                                                });
                                    }
                                } else {
                                    // Ошибка входа
                                    Toast.makeText(getContext(), "Login failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Please fill both fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.Register.setOnClickListener(view -> {
            listeners.switchToRegister();
        });


        return binding.getRoot();
    }
}
