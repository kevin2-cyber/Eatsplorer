package com.kimikevin.eatsplorer.view;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private ProgressBar bar;

    private FirebaseAuth auth;

    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // configure progressbar
        bar = new ProgressBar(this);
        bar.setVisibility(View.GONE);

        // init FirebaseAuth
        auth = FirebaseAuth.getInstance();

         // navigate to SignUpActivity
        binding.tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent
            );
        });
            binding.showPassBtn.setOnClickListener(this::togglePassword);

        binding.btnSignIn.setOnClickListener(view -> validateData());
    }


    // navigate to OnboardingActivity upon successful login
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
        } else if (password.length() < 6) {
            binding.etPassword.setError("Password must be more than six characters",
                    AppCompatResources.getDrawable(this, R.drawable.baseline_close_24));
        } else {
            login();
        }
    }

    // toggle password
    private void togglePassword(View view) {
        if (view.getId() == binding.showPassBtn.getId()) {
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

    private void login() {
        // show progress
        bar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(task -> {
                    // login successful
                    bar.setVisibility(View.GONE);
                    // get user info
                    FirebaseUser user = auth.getCurrentUser();
                    assert user != null;
                    String email = user.getEmail();
                    Toast.makeText(this,"logged in as " + email, Toast.LENGTH_LONG)
                            .show();

                    // open profile
                    Intent intent = new Intent(this, MapsActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(task -> {
                    // login failed
                    bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(this,"Login failed due to " + task.getMessage(), Toast.LENGTH_LONG)
                            .show();
                });
    }
}