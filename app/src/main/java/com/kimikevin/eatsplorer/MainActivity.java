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

import java.util.ArrayList;
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




        setupOnboardingItems();

        // initializing the ViewPager object
        onboardingViewPager = binding.viewPager;
        onboardingViewPager.setAdapter(onboardingAdapter);


    }

    private void setupOnboardingItems() {
        onboardings = new ArrayList<>();

        Onboarding first = new Onboarding();
        first.setTitle("Satisfy your cravings \nwith ease");
        first.setDescription("Integer a viverra sit feugiat leo\nncommodo nunc.");
        first.setImage(R.drawable.onboarding_image_1);

        Onboarding second = new Onboarding();
        second.setTitle("Find your new favourite \nrestaurant with just a tap");
        second.setDescription("Integer a viverra sit feugiat leo\nncommodo nunc.");
        second.setImage(R.drawable.onboarding_image_2);

        Onboarding third = new Onboarding();
        third.setTitle("Fresh meals, delivered to your doorstep");
        third.setDescription("Integer a viverra sit feugiat leo\nncommodo nunc.");
        third.setImage(R.drawable.onboarding_image_3);

        onboardings.add(first);
        onboardings.add(second);
        onboardings.add(third);

        onboardingAdapter = new OnboardingAdapter(onboardings, this);
    }

}