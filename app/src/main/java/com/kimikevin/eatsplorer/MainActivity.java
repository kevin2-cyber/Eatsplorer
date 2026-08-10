package com.kimikevin.eatsplorer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.kimikevin.eatsplorer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "eatsplorer_prefs";
    public static final String KEY_ONBOARDING_COMPLETE = "onboarding_complete";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean onboardingComplete = prefs.getBoolean(KEY_ONBOARDING_COMPLETE, false);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            
            // Set dynamic start destination based on onboarding status
            NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.nav_graph);
            if (onboardingComplete) {
                navGraph.setStartDestination(R.id.homeFragment);
            } else {
                navGraph.setStartDestination(R.id.onboardingFragment);
            }
            navController.setGraph(navGraph);

            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

            // Hide bottom navigation on onboarding screen
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (destination.getId() == R.id.onboardingFragment) {
                    binding.bottomNavigation.setVisibility(View.GONE);
                } else {
                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
