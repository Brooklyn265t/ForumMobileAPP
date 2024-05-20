package com.example.forum;

import static androidx.core.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.forum.databinding.ProfileLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;


public class profile extends AppCompatActivity {
    private ProfileLayoutBinding binding;
    private FirebaseAuth auth; // Инстанс FirebaseAuth
    forum frag3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        frag3 = new forum();

        Intent data = getIntent();

        String login = data.getStringExtra("login");
        String email = data.getStringExtra("email");
        String role = data.getStringExtra("role");

        TextView loginTextView = binding.Login;
        loginTextView.setText(login);

        TextView emailTextView = binding.mailadress;
        emailTextView.setText(email);

        TextView roleTextView = binding.Role;
        roleTextView.setText(role);


        Intent intent = new Intent(this, MainActivity.class);
        binding.extfromaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.forum) {
            ((MainActivity.OnForumReplaceListener) this).onForumReplaceRequested(frag3);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
