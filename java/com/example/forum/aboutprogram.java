package com.example.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.forum.databinding.ActivityAboutappBinding;

public class aboutprogram extends AppCompatActivity {
    ActivityAboutappBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutappBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрытие текущей активности и возврат к предыдущей активности
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItemToHide = menu.findItem(R.id.aboutprogramm);
        if (menuItemToHide!= null) {
            menuItemToHide.setVisible(false);
        }
        MenuItem menuItemToHide2 = menu.findItem(R.id.forum);
        if (menuItemToHide2!= null) {
            menuItemToHide2.setVisible(false);
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent click;
        if (item.getItemId() == R.id.me) {
            click = new Intent(this, aboutme.class);
            startActivity(click);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}