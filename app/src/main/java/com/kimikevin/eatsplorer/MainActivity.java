package com.kimikevin.eatsplorer;

import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationBarView;
import com.kimikevin.eatsplorer.databinding.ActivityMainBinding;
import com.kimikevin.eatsplorer.view.MapsFragment;
import com.kimikevin.eatsplorer.view.OnboardingActivity;
import com.kimikevin.eatsplorer.view.RestaurantListFragment;
import com.kimikevin.eatsplorer.viewmodel.SplashViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationBarView.OnItemSelectedListener {

    public static final String PREFS_NAME = "eatsplorer_prefs";
    public static final String KEY_ONBOARDING_COMPLETE = "onboarding_complete";

    private ActivityMainBinding binding;
    private final RestaurantListFragment restaurantListFragment = new RestaurantListFragment();
    private final MapsFragment mapsFragment = new MapsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean onboardingComplete = prefs.getBoolean(KEY_ONBOARDING_COMPLETE, false);

        if (onboardingComplete) {
            setupBottomNavigation();
        } else {
            SplashViewModel viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
            splashScreen.setKeepOnScreenCondition(() -> {
                Boolean ready = viewModel.isDataReady().getValue();
                return ready == null || !ready;
            });
            viewModel.isDataReady().observe(this, isDataReady -> {
                if (Boolean.TRUE.equals(isDataReady)) {
                    startActivity(new Intent(this, OnboardingActivity.class));
                    finish();
                }
            });
        }
    }

    private void setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener(this);
        binding.bottomNavigationView.setSelectedItemId(R.id.navigation_list);
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, fragment)
                .setTransition(TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.navigation_list) {
            switchFragment(restaurantListFragment);
            return true;
        } else if (itemId == R.id.navigation_map) {
            switchFragment(mapsFragment);
            return true;
        }
        return false;
    }
}
