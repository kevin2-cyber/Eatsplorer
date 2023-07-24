package com.kimikevin.eatsplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.kimikevin.eatsplorer.databinding.ActivityMainBinding;
import com.kimikevin.eatsplorer.model.Onboarding;
import com.kimikevin.eatsplorer.view.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Onboarding> onboarding = new ArrayList<>();
    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;

    TabLayout mTabLayout;
    Button mButton;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        onboarding.add(
                new Onboarding("Satisfy your cravings",R.drawable.onboarding_image_1,
                        "with","ease"));
        onboarding.add(
                new Onboarding("Find your new favourite",R.drawable.onboarding_image_2,
                        "restaurant with","just a tap"));
        onboarding.add(
                new Onboarding("Fresh meals, delivered to",R.drawable.onboarding_image_3,
                        "your","doorstep"));

        // initializing the ViewPager object
        mViewPager = binding.viewPagerMain;

        // initializing the TabLayout object
        mTabLayout = binding.tabLayout;

        // initializing the ViewPagerAdapter Object
        mViewPagerAdapter = new ViewPagerAdapter(this);

        mViewPager.setAdapter(mViewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }
}