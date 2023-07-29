package com.kimikevin.eatsplorer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.kimikevin.eatsplorer.R;
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

        name = mBinding.etFullName.getText().toString().trim();
        email = mBinding.etEmailAddress.getText().toString().trim();
        password = mBinding.etPass.getText().toString().trim();

        mBinding.btnRegister.setOnClickListener(view -> {
            if (name != null) {
                Toast.makeText(this, "Hey", Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.tvSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        mBinding.etPass.setOnClickListener(this::togglePassword);
    }

    // toggle password
    private void togglePassword(View view) {
        if (view.getId() == mBinding.etPass.getId()) {
            if(mBinding.etPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.baseline_visibility_off_24);
                // show password
                mBinding.etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.baseline_visibility_24);
                // Hide password
                mBinding.etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}