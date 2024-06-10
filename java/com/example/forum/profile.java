package com.example.forum;

import static androidx.core.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.forum.databinding.ProfileLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class profile extends AppCompatActivity {
    private ProfileLayoutBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userId;
    private String Email;
    private String Name;
    private String Role;
    private String Password;
    private TextView EmailTextView;
    private TextView NameTextView;
    private TextView RoleTextView;
    private EditText changeEmail;
    private EditText changePassword;
    private String newEmail;
    private String newPassword;
    private CustomDialogFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user!= null) {
            userId = user.getUid();
            if (userId!= null) {
                db.collection("users").document(userId)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                Email = documentSnapshot.getString("email");
                                Name = documentSnapshot.getString("login");
                                Role = documentSnapshot.getString("role");
                                TextViewData(Email, Name, Role);
                                Log.d("FirestoreUserData", "Данные пользователя успешно получены: Email=" + Email + ", Name=" + Name + ", Role=" + Role);
                            } else {
                                Log.d("FirestoreUserData", "Нет документа с этим id");
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e("FirestoreUserData", "Ошибка при получении данных пользователя", e);
                        });
            } else {
                Toast.makeText(profile.this, "ID пользователя не найден", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(profile.this, "Ошибка загрузки пользователя", Toast.LENGTH_LONG).show();
        }
        initUI();
    }

    private void initUI() {
        binding.ChangeEmail.setEnabled(true);
        binding.ChangePassword.setEnabled(true);
        binding.savedata.setEnabled(false);
        // Настройка слушателей кликов
        setupClickListeners();
    }
    private void setupClickListeners() {
        binding.extfromaccount.setOnClickListener(v -> ExitFromAccount());
        binding.changedata.setOnClickListener(v -> ChangeData());
        binding.savedata.setOnClickListener(view -> UpdateData());
    }
    private void TextViewData(String Email, String Name, String Role){

        EmailTextView = binding.mailadress;
        EmailTextView.setText(Email);

        NameTextView = binding.Name;
        NameTextView.setText(Name);

        RoleTextView = binding.Role;
        RoleTextView.setText(Role);
    }

    private void ChangeData() {
        binding.ChangeEmail.setEnabled(true);
        binding.ChangePassword.setEnabled(true);
        binding.savedata.setEnabled(true);
        binding.changedata.setEnabled(false);;
        changeEmail = binding.ChangeEmail;
        changePassword = binding.ChangePassword;
        newEmail = changeEmail.getText().toString();
        newPassword = changePassword.getText().toString();
    }
    private void UpdateData(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            user.verifyBeforeUpdateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(profile.this, "Email успешно изменен", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(profile.this, "Ошибка при изменении email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            if (!newPassword.isEmpty()) {
                auth.getCurrentUser().updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(profile.this, "Пароль успешно изменен", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(profile.this, "Ошибка при изменении пароля", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(profile.this, "Введите новый пароль", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void ExitFromAccount(){
        dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.forum) {
            Intent forum = new Intent(profile.this, MainActivity.class);
            forum.putExtra("forum", "changeme");
            forum.putExtra("name", Name);
            startActivity(forum);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
