package com.kimikevin.eatsplorer.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.transition.Explode;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.ActivityLoginBinding;


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

//        // Inside your activity (if you did not enable transitions in your theme)
//        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//
//        // Set an exit transition
//        getWindow().setExitTransition(new Explode());

        binding.btnSignIn.setOnClickListener(view -> validateData());

         // navigate to SignUpActivity
        binding.tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent
//                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            );
        });
            binding.showPassBtn.setOnClickListener(this::togglePassword);
    }

    //TODO: add login auth(email and password sign in and oauth)
    //TODO: Add database

    //TODO: link database

    // navigate to HomeActivity upon successful login
    private void validateData(){
        // get data
        email = binding.etEmail.getText().toString().trim();
        password = binding.etPassword.getText().toString().trim();

        // validate user
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid email format
            binding.etEmail.setError("Invalid Email Address",
                    AppCompatResources.getDrawable(this, R.drawable.baseline_close_24));
        } else if (TextUtils.isEmpty(password)) {
            // no password entered
            binding.etPassword.setError("no password entered",
                    AppCompatResources.getDrawable(this, R.drawable.baseline_close_24));
            System.out.println();
        } else if (password.length() < 6) {
            binding.etPassword.setError("Password must be more than six characters",
                    AppCompatResources.getDrawable(this, R.drawable.baseline_close_24));
        } else {
            startActivity(new Intent(this, MapsActivity.class));
        }
    }

    // toggle password
    private void togglePassword(View view) {
        if (view.getId() == binding.etPassword.getId()) {
            if(binding.etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.baseline_visibility_off_24);
                // show password
                binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.baseline_visibility_24);
                // Hide password
                binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}