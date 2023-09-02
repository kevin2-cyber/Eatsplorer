package com.kimikevin.eatsplorer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.ActivityRegisterBinding;
import com.kimikevin.eatsplorer.model.entity.User;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private ProgressBar bar;
    private FirebaseAuth auth;
    String name = "";
    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        binding.btnRegister.setOnClickListener(view -> {
            validateData();
        });

        // configure progress dialog
        bar = new ProgressBar(this);
        bar.setVisibility(View.GONE);

        binding.tvSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        binding.showPassBtn.setOnClickListener(this::togglePassword);

        auth = FirebaseAuth.getInstance();

    }

    // validate data
    private void validateData() {
        // get the data
        name = binding.etFullName.getText().toString().trim();
        email = binding.etEmailAddress.getText().toString().trim();
        password = binding.etPass.getText().toString().trim();

        // validate the data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid email format
            binding.etEmailAddress.setError("Invalid Email format");
        } else if (TextUtils.isEmpty(password)) {
            // password isn't entered
            binding.etPass.setError("Please enter your password");
        } else if (password.length() < 6) {
            binding.etPass.setError("Please enter at least 6 characters long");
        } else if (name.isEmpty()) {
            binding.etFullName.setError("Please enter your name");
        } else register();
    }

    private void register() {
        //show progress
        bar.setVisibility(View.VISIBLE);

        // create account
        auth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(task -> {

                    // dismiss progress
                    bar.setVisibility(View.GONE);
                    User user = (User) auth.getCurrentUser();
                    assert user != null;
                    String email = user.getEmail();
                    Toast.makeText(this, "Account created with " + email, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    //signup failed
                    bar.setVisibility(View.GONE);
                    Toast.makeText(this,"Sign up failed due to " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    // toggle password
    private void togglePassword(View view) {
        if (view.getId() == binding.showPassBtn.getId()) {
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

    @Override
    public boolean onSupportNavigateUp() {
        // go back to previous activity, when back button of actionbar clicked
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}