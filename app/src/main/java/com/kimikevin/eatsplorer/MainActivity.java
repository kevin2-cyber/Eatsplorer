package com.kimikevin.eatsplorer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;


import com.kimikevin.eatsplorer.databinding.ActivityMainBinding;
import com.kimikevin.eatsplorer.view.OnboardingActivity;
import com.kimikevin.eatsplorer.viewmodel.SplashViewModel;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        SplashViewModel viewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        // Keep the splash screen on until the loading is complete
        splashScreen.setKeepOnScreenCondition(() -> {
            Boolean ready = viewModel.isDataReady().getValue();
            return ready == null || !ready;
        });

        // Observe the loading status to know when to transition
        viewModel.isDataReady().observe(this, isDataReady -> {
            if (Boolean.TRUE.equals(isDataReady)) {
                // Start the next activity or update the UI
                proceedToMainContent();
            }
        });

    }

    private void proceedToMainContent() {
        Intent intent = new Intent(this, OnboardingActivity.class);
        startActivity(intent);
        finish();
    }


}