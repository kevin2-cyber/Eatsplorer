package com.kimikevin.eatsplorer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;


import com.kimikevin.eatsplorer.databinding.ActivityMainBinding;
import com.kimikevin.eatsplorer.view.HomeActivity;
import com.kimikevin.eatsplorer.viewmodel.SplashViewModel;



public class MainActivity extends AppCompatActivity {
    SplashViewModel viewModel;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        // Keep the splash screen on until the loading is complete
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            Boolean isLoading = viewModel.getLoadingStatus().getValue();
            return isLoading == null || !isLoading;
        });

        // Observe the loading status to know when to transition
        viewModel.getLoadingStatus().observe(this, isLoadingComplete -> {
            if (Boolean.TRUE.equals(isLoadingComplete)) {
                // Start the next activity or update the UI
                proceedToMainContent();
            }
        });

    }

    private void proceedToMainContent() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


}