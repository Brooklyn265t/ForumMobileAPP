package com.example.forum;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.forum.databinding.FragmentRegistrBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class register extends Fragment {
    private FragmentRegistrBinding binding;
    private FirebaseAuth auth; // Инстанс FirebaseAuth
    private String admin = "admin";
    private String user = "user";

    public interface FragmentRegisterListeners {
        public void switchToLogin();
    }

    register.FragmentRegisterListeners listeners;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listeners = (register.FragmentRegisterListeners) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentRegisterListeners");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance(); // Инициализация FirebaseAuth
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrBinding.inflate(inflater, container, false);

        binding.Registraccount.setOnClickListener(view -> {
            // Assuming that email and password are obtained from input fields
            String login = binding.editTextText.getText().toString();
            String email = binding.editTextTextEmailAddress.getText().toString();
            String password = binding.editTextTextPassword.getText().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> userRole = new HashMap<>();
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Registration was successful
                            User newUser;
                            if (email == "ivan.belog.44@gmail.com"){
                                newUser = new User(email, login, "admin");
                                db.collection("users")
                                        .document(auth.getCurrentUser().getUid()) // Используем userID из Authentication
                                        .set(newUser, SetOptions.merge()) // Мержим новые данные с существующими
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(getContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getActivity(), profile.class);
                                            intent.putExtra("login", login);
                                            intent.putExtra("email", email);
                                            intent.putExtra("role", newUser.getRole());
                                            startActivity(intent);
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(getContext(), "Failed to save data to Firestore", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                newUser = new User(email, login, "user");
                                db.collection("users")
                                        .document(auth.getCurrentUser().getUid()) // Используем userID из Authentication
                                        .set(newUser, SetOptions.merge()) // Мержим новые данные с существующими
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(getContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getActivity(), profile.class);
                                            intent.putExtra("login", login);
                                            intent.putExtra("email", email);
                                            intent.putExtra("role", newUser.getRole());
                                            startActivity(intent);
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(getContext(), "Failed to save data to Firestore", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } 
                        else {
                            // Registration error
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(getContext(), "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        binding.toLogin.setOnClickListener(view -> {
            listeners.switchToLogin();
        });

        return binding.getRoot();
    }
}