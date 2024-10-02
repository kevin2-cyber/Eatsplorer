package com.kimikevin.eatsplorer.view;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kimikevin.eatsplorer.databinding.ActivityHomeBinding;


public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        // init auth
        auth = FirebaseAuth.getInstance();
        checkUser();

        // handle click, logout
        binding.btnLogout.setOnClickListener( view -> {
            auth.signOut();
            checkUser();
        });
    }

    @SuppressLint("SetTextI18n")
    private void checkUser() {
        // check if user is logged in or not
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {

            // user is not null, user is logged in, get user info
            String email = user.getEmail();

            // set to text view
            binding.username.setText(" You are logged in as " + email);
        } else {

            //user is null, user not logged in go to login activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}