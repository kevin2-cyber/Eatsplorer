package com.kimikevin.eatsplorer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.kimikevin.eatsplorer.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding mBinding;
    String name = "";
    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
        setContentView(mBinding.getRoot());

        name = mBinding.etName.getText().toString().trim();
        email = mBinding.etEmail.getText().toString().trim();
        password = mBinding.etPassword.getText().toString().trim();

        mBinding.btnRegister.setOnClickListener(view -> {
            if (name != null) {
                Toast.makeText(this, "Hey", Toast.LENGTH_SHORT).show();
            } getViewModelStore();
        });
    }
}