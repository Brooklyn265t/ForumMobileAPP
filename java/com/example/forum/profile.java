package com.example.forum;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.forum.databinding.ProfileLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private TextView EmailTextView;
    private TextView NameTextView;
    private TextView RoleTextView;
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
        // Настройка слушателей кликов
        setupClickListeners();
    }
    private void setupClickListeners() {
        binding.extfromaccount.setOnClickListener(v -> ExitFromAccount());
    }
    private void TextViewData(String Email, String Name, String Role){

        EmailTextView = binding.mailadress;
        EmailTextView.setText(Email);

        NameTextView = binding.Name;
        NameTextView.setText(Name);

        RoleTextView = binding.Role;
        RoleTextView.setText(Role);
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
        Intent click;
        if (item.getItemId() == R.id.program) {
            click  = new Intent(this, aboutprogram.class);
            startActivity(click);
            return true;
        }
        if (item.getItemId() == R.id.me) {
            click = new Intent(this, aboutme.class);
            startActivity(click);
            return true;
        }
        if (item.getItemId() == R.id.forum) {
            click = new Intent(profile.this, MainActivity.class);
            click.putExtra("forum", "changeme");
            startActivity(click);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}