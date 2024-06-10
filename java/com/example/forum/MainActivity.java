package com.example.forum;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.forum.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements login.FragmentLoginListeners, register.FragmentRegisterListeners, ForumFragment.FragmentForumListeners, ForumFragment.FragmentTopicDescriptionListeners, TopicDescriptionFragment.FragmentTopicDescriptionListeners {
    FragmentTransaction fTrans;
    login frag1;
    register frag2;
    ForumFragment frag3;
    TopicDescriptionFragment frag4;
    ActivityMainBinding binding;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        frag1 = new login();
        frag2 = new register();
        frag3 = new ForumFragment();
        frag4 = new TopicDescriptionFragment();

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(binding.screen.getId(), frag1);
        fTrans.commit();
        String value = getIntent().getStringExtra("forum");
        if (Objects.equals(value, "changeme")){
            switchToForum();
            //Log.d("aaaa", "i work");
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
    public void switchToForum() {
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(binding.screen.getId(), frag3);
        fTrans.commit();
        //Log.d("aaaa", "i do it");
    }

    public void switchToTopic(String title) {
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(binding.screen.getId(), frag4);
        args = new Bundle();
        args.putString("title", title);
        frag4.setArguments(args); // Передаем Bundle в TopicDescriptionFragment
        fTrans.replace(binding.screen.getId(), frag4);
        fTrans.commit();
        //Log.d("aaaa", "i do it");
    }
}