package com.example.forum;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.forum.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements login.FragmentLoginListeners, register.FragmentRegisterListeners {
    FragmentTransaction fTrans;
    login frag1;
    register frag2;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        frag1 = new login();
        frag2 = new register();

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(binding.screen.getId(), frag1);
        fTrans.commit();

        Intent intent = getIntent();
        if (intent.hasExtra("forumFrag")) {
            forum frag3 = (forum) intent.getSerializableExtra("forumFrag");
            switchToForum(frag3);
        }
    }

    @Override
    public void switchToRegister() {
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(binding.screen.getId(), frag2);
        fTrans.commit();
    }

    @Override
    public void switchToLogin() {
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(binding.screen.getId(), frag1);
        fTrans.commit();
    }
    // Создаем транзакцию для замены текущего фрагмента на фрагмент форума

    public void switchToForum(forum frag3) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.screen.getId(), frag3);
        transaction.commit(); // Применяем транзакцию
    }
    public interface OnForumReplaceListener {
        void onForumReplaceRequested(forum frag3);

        void onFragmentReplaceRequested(forum frag3);
    }

}