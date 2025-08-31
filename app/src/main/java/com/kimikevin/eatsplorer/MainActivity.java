package com.kimikevin.eatsplorer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);


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


//    private void setupOnboardingIndicators() {
//        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
//        );
//        params.setMargins(8,0,8,0);
//        for (int i = 0; i < indicators.length; i++) {
//            indicators[i] = new ImageView(getApplicationContext());
//            indicators[i].setImageDrawable(
//                    ContextCompat.getDrawable(
//                        getApplicationContext(),
//                        R.drawable.default_dot
//            ));
//            indicators[i].setLayoutParams(params);
//            onboardingIndicators.addView(indicators[i]);
//        }
//    }

//    private void setCurrentOnboardingIndicator(int index) {
//        int childCount = onboardingIndicators.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            ImageView imageView = (ImageView) onboardingIndicators.getChildAt(i);
//            if (i == index) {
//                imageView.setImageDrawable(
//                        ContextCompat.getDrawable(
//                                getApplicationContext(),
//                                R.drawable.selected_dot
//                        )
//                );
//            } else {
//                imageView.setImageDrawable(
//                        ContextCompat.getDrawable(
//                                getApplicationContext(),
//                                R.drawable.default_dot
//                        )
//                );
//            }
//        }
//        if (index == onboardingAdapter.getItemCount() - 1) {
//            nextBtn.setText(R.string.get_started);
//        } else {
//            nextBtn.setText(getString(R.string.next));
//        }
//    }


}