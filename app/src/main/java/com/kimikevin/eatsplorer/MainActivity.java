package com.kimikevin.eatsplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.kimikevin.eatsplorer.databinding.ActivityMainBinding;
import com.kimikevin.eatsplorer.model.Onboarding;
import com.kimikevin.eatsplorer.view.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Onboarding[] onboarding;
    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;

    TabLayout mTabLayout;
    Button mButton;
    ActivityMainBinding binding;

//    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                setDotIndicator(position);
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
        getSplashScreen().setOnExitAnimationListener(splashScreenView -> {
            final ObjectAnimator slideUp = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.getHeight()
            );
            slideUp.setInterpolator(new AnticipateInterpolator());
            slideUp.setDuration(200L);

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    splashScreenView.remove();
                }
            });

            // Run your animation.
            slideUp.start();
        });
        setContentView(binding.getRoot());

        onboarding = new Onboarding[] {
                new Onboarding("Satisfy your cravings",R.drawable.onboarding_image_1,
                        "with","ease"),
                new Onboarding("Find your new favourite",R.drawable.onboarding_image_2,
                        "restaurant with","just a tap"),
                new Onboarding("Fresh meals, delivered to",R.drawable.onboarding_image_3,
                        "your","doorstep")
        };

        // initializing the ViewPager object
        mViewPager = binding.viewPagerMain;

        // initializing the TabLayout object
        mTabLayout = binding.tabLayout;

        // initializing the ViewPagerAdapter Object
        mViewPagerAdapter = new ViewPagerAdapter(this, onboarding);

        mViewPager.setAdapter(mViewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

//    public void setDotIndicator(int position) {
//        binding.viewPagerMain.removeAllViews();
//    }
//
//    private int getItem(int i) {
//        return mViewPager.getCurrentItem() + i;
//    }
}