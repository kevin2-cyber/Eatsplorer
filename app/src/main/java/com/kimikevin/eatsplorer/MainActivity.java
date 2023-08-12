package com.kimikevin.eatsplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.kimikevin.eatsplorer.databinding.ActivityMainBinding;
import com.kimikevin.eatsplorer.model.Onboarding;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    Onboarding[] onboarding;
    ViewPager2 onboardingViewPager;
    OnboardingAdapter mOnboardingAdapter;

    TabLayout mTabLayout;
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

        getStartedBtn = findViewById(R.id.get_started_btn);
        nextBtn = findViewById(R.id.next_btn);
        skipBtn = findViewById(R.id.skip_btn);

        onboarding = new Onboarding[] {
                new Onboarding("Satisfy your cravings",R.drawable.onboarding_image_1,
                        "with","ease"),
                new Onboarding("Find your new favourite",R.drawable.onboarding_image_2,
                        "restaurant with","just a tap"),
                new Onboarding("Fresh meals, delivered to",R.drawable.onboarding_image_3,
                        "your","doorstep")
        };



        // initializing the ViewPager object
        onboardingViewPager = binding.viewPager;

        // initializing the TabLayout object
        mTabLayout = binding.tabLayout;

        // initializing the ViewPagerAdapter Object
        mOnboardingAdapter = new OnboardingAdapter(this, onboarding);

        onboardingViewPager.setAdapter(mOnboardingAdapter);

        mTabLayout.setupWithViewPager(onboardingViewPager);
        onboardingViewPager.addOnPageChangeListener(pageChangeListener);

    }

    public void setDotIndicator(int position) {
        binding.viewPagerMain.removeAllViews();
    }

    private int getItem(int i) {
        return onboardingViewPager.getCurrentItem() + i;
    }
}