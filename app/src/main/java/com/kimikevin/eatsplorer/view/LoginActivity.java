package com.kimikevin.eatsplorer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}