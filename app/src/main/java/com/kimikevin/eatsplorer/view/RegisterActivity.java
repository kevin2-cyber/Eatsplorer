package com.kimikevin.eatsplorer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.ActivityRegisterBinding;
import com.kimikevin.eatsplorer.model.entity.User;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    String name = "";
    String email = "";
    String password = "";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

//        // Inside your activity (if you did not enable transitions in your theme)
//        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//
//        // Set an exit transition
//        getWindow().setExitTransition(new Explode());

        binding.btnRegister.setOnClickListener(view -> {
            if (name != null) {
                Toast.makeText(this, "Hey", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.etPass.setOnClickListener(this::togglePassword);
    }

    // validate data
    private void validateData() {
        name = binding.etFullName.getText().toString().trim();
        email = binding.etEmailAddress.getText().toString().trim();
        password = binding.etPass.getText().toString().trim();
    }

    // toggle password
    private void togglePassword(View view) {
        if (view.getId() == binding.etPass.getId()) {
            if(binding.etPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.baseline_visibility_off_24);
                // show password
                binding.etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.baseline_visibility_24);
                // Hide password
                binding.etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}