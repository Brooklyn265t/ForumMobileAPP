package com.example.forum;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
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
}