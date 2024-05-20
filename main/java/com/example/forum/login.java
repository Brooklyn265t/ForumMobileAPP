package com.example.forum;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.forum.databinding.FragmentLoginBinding;

public class login extends Fragment {
    private FragmentLoginBinding binding;

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
                String login = binding.editTextText.getText().toString();
                String password = binding.editTextTextPassword.getText().toString();

                Intent intent = new Intent(getActivity(), profile.class); // Замените MainActivity.class на класс Activity, к которому вы хотите перейти
                startActivity(intent);
            }
        });

        binding.Register.setOnClickListener(view -> {
            listeners.switchToRegister();
        });

        return binding.getRoot();
    }
}
