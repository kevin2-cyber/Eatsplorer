package com.kimikevin.eatsplorer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.kimikevin.eatsplorer.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnSignIn.setOnClickListener(view -> {
            email = binding.etEmail.getText().toString().trim();
            password = binding.etPass.getText().toString().trim();

            if (Objects.equals(email, "kimikevin@zoho.com") && password.equals("asdfzxcvbnm")) {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //TODO: add login auth(email and password sign in and oauth)
    //TODO: Add database

    //TODO: link database

    // navigate to HomeActivity upon successful login
}