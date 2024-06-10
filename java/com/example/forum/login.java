package com.example.forum;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.forum.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends Fragment {
    private FragmentLoginBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String emailText;
    private String passwordText;
    private String userId;

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
                emailText = binding.editTextTextEmailAddress.getText().toString();
                passwordText = binding.editTextTextPassword.getText().toString();
                if (!TextUtils.isEmpty(emailText) &&!TextUtils.isEmpty(passwordText)) {
                    auth.signInWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = auth.getCurrentUser();
                                    if (currentUser!= null) {
                                        userId = currentUser.getUid();
                                        db.collection("users").document(userId)
                                                .get()
                                                .addOnCompleteListener(userTask -> {
                                                    if (userTask.isSuccessful()) {
                                                        DocumentSnapshot userDoc = userTask.getResult();
                                                        if (userDoc.exists()) {
                                                            Intent intent = new Intent(getActivity(), profile.class);
                                                            startActivity(intent);
                                                        } else {
                                                            // Документ пользователя не найден
                                                            Log.d("FirestoreUserData", "Нет документа с этим id");
                                                        }
                                                    } else {
                                                        // Ошибка при получении данных пользователя
                                                        Log.d("FirestoreUserData", "Не получено ", userTask.getException());
                                                    }
                                                });
                                    }
                                } else {
                                    // Ошибка входа
                                    Toast.makeText(getContext(), "Ошибка входа.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.Register.setOnClickListener(view -> {
            listeners.switchToRegister();
        });
        return binding.getRoot();
    }
}