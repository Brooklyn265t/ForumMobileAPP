package com.example.forum;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.databinding.FragmentForumBinding;

import java.util.Arrays;
import java.util.List;


public class forum extends Fragment {
    private FragmentForumBinding binding;
    private RecyclerView recyclerView;
    private TopicAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Используем View Binding для инфляции макета
        FragmentForumBinding binding = FragmentForumBinding.inflate(inflater, container, false);
        // Получаем ссылку на RecyclerView через View Binding
        RecyclerView recyclerView = binding.recyclerView;
        // Устанавливаем менеджер макета для RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Пример списка тем
        List<Topic> topics = Arrays.asList(
                new Topic("Тема 1", "Описание темы 1"),
                new Topic("Тема 2", "Описание темы 2")
        );
        // Создаем и устанавливаем адаптер для RecyclerView
        TopicAdapter adapter = new TopicAdapter(topics);
        recyclerView.setAdapter(adapter);
        // Возвращаем корневой виджет из View Binding
        return binding.getRoot();
    }
}