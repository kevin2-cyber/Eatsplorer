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

    TabLayout tabLayout;
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





        // initializing the ViewPager object
        onboardingViewPager = binding.viewPager;

        // initializing the TabLayout object
        tabLayout = binding.tabLayout;

        onboardingAdapter = new OnboardingAdapter(onboardings, this);

        onboardingViewPager.setAdapter(onboardingAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout , onboardingViewPager, (tab, position) -> tab.setText(LOG_TAG + (position + 1)));
        tabLayoutMediator.attach();

//        // initializing the ViewPagerAdapter Object
//        mOnboardingAdapter = new OnboardingAdapter(this, onboarding);
//
//        onboardingViewPager.setAdapter(mOnboardingAdapter);
//
//        mTabLayout.setupWithViewPager(onboardingViewPager);
//        onboardingViewPager.addOnPageChangeListener(pageChangeListener);

    }

}