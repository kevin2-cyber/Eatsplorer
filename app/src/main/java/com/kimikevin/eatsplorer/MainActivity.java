package com.kimikevin.eatsplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.kimikevin.eatsplorer.databinding.ActivityMainBinding;
import com.kimikevin.eatsplorer.model.Onboarding;
import com.kimikevin.eatsplorer.view.RegisterActivity;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    Onboarding[] onboarding;
    ViewPager2 onboardingViewPager;
    OnboardingAdapter mOnboardingAdapter;

    TabLayout mTabLayout;
    Button getStartedBtn, nextBtn, skipBtn;
    ActivityMainBinding binding;
    public static final String LOG_TAG = MainActivity.class.getSimpleName().toLowerCase(Locale.ROOT);

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);
            if (position < 2) {
                getStartedBtn.setVisibility(View.GONE);
                skipBtn.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.VISIBLE);
            } else {
                getStartedBtn.setVisibility(View.VISIBLE);
                skipBtn.setVisibility(View.GONE);
                nextBtn.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
//        getSplashScreen().setOnExitAnimationListener(splashScreenView -> {
//            final ObjectAnimator slideUp = ObjectAnimator.ofFloat(
//                    splashScreenView,
//                    View.TRANSLATION_Y,
//                    0f,
//                    -splashScreenView.getHeight()
//            );
//            slideUp.setInterpolator(new AnticipateInterpolator());
//            slideUp.setDuration(200L);
//
//            // Call SplashScreenView.remove at the end of your custom animation.
//            slideUp.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    splashScreenView.remove();
//                }
//            });
//
//            // Run your animation.
//            slideUp.start();
//        });
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

        nextBtn.setOnClickListener(view -> {
            if (getItem(0) < 2)
                onboardingViewPager.setCurrentItem(getItem(1), true);
            else {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        skipBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });


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