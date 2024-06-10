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
import java.util.Objects;

public class register extends Fragment {
    private FragmentRegistrBinding binding;
    private FirebaseAuth auth; // Инстанс FirebaseAuth
    private String Admin = "admin";
    private String User = "user";
    private String Name;
    private String Email;
    private String Password;
    private String Password2;

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
                    Name = binding.editTextText.getText().toString();
                    Email = binding.editTextTextEmailAddress.getText().toString();
                    Password = binding.editTextTextPassword.getText().toString();
                    Password2 = binding.editTextTextPassword2.getText().toString();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    if (!Objects.equals(Password, Password2)) {
                        Toast.makeText(getContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.createUserWithEmailAndPassword(Email, Password)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        User newUser;
                                        if (Email.equals("ivan.belog.44@gmail.com")) {
                                            newUser = new User(Email, Name, "admin");
                                            db.collection("users")
                                                    .document(auth.getCurrentUser().getUid()) // Используем userID из Authentication
                                                    .set(newUser, SetOptions.merge()) // Мержим новые данные с существующими
                                                    .addOnSuccessListener(aVoid -> {
                                                        Toast.makeText(getContext(), "Успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getActivity(), profile.class);
                                                        startActivity(intent);
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(getContext(), "Ошибка сохранение данных в firestore", Toast.LENGTH_SHORT).show();
                                                    });
                                        }
                                        else {
                                            newUser = new User(Email, Name, "user");
                                            db.collection("users")
                                                    .document(auth.getCurrentUser().getUid()) // Используем userID из Authentication
                                                    .set(newUser, SetOptions.merge()) // Мержим новые данные с существующими
                                                    .addOnSuccessListener(aVoid -> {
                                                        Toast.makeText(getContext(), "Успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getActivity(), profile.class);
                                                        startActivity(intent);
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(getContext(), "Ошибка сохранение данных в firestore", Toast.LENGTH_SHORT).show();
                                                    });
                                        }
                                    }
                                    else {
                                        String errorMessage = task.getException().getMessage();
                                        Toast.makeText(getContext(), "Ошибка регистрации: " + errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
        binding.toLogin.setOnClickListener(view -> {
            listeners.switchToLogin();
        });

        return binding.getRoot();
    }
}