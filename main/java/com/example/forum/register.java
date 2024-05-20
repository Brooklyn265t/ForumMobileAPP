package com.example.forum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.forum.databinding.FragmentLoginBinding;
import com.example.forum.databinding.FragmentRegistrBinding;

public class register extends Fragment {
    private FragmentRegistrBinding binding;
    public interface FragmentRegisterListeners{
        public void switchToLogin();
    }
    register.FragmentRegisterListeners listeners;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listeners = (register.FragmentRegisterListeners) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement FragmentRegisterListeners");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrBinding.inflate(inflater, container, false);

        binding.Registraccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), profile.class); // Замените MainActivity.class на класс Activity, к которому вы хотите перейти
                startActivity(intent);
            }
        });

        binding.toLogin.setOnClickListener(view -> {
            listeners.switchToLogin();
        });
        return binding.getRoot();
    }

}
