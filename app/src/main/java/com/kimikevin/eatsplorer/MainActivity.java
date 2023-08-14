package com.kimikevin.eatsplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kimikevin.eatsplorer.databinding.ActivityMainBinding;
import com.kimikevin.eatsplorer.model.Onboarding;
import com.kimikevin.eatsplorer.view.adapter.OnboardingAdapter;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    List<Onboarding> onboardings;

    ViewPager2 onboardingViewPager;
    OnboardingAdapter onboardingAdapter;

    Button getStartedBtn, nextBtn, skipBtn;
    ActivityMainBinding binding;
    public static final String LOG_TAG = MainActivity.class.getSimpleName().toLowerCase(Locale.ROOT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);

        setContentView(binding.getRoot());

        getStartedBtn = binding.getStartedBtn;
        nextBtn = binding.nextBtn;
        skipBtn = binding.skipBtn;





        // initializing the ViewPager object
        onboardingViewPager = binding.viewPager;



        onboardingAdapter = new OnboardingAdapter(onboardings, this);

        onboardingViewPager.setAdapter(onboardingAdapter);


    }

}