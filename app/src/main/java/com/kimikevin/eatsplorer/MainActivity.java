package com.kimikevin.eatsplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kimikevin.eatsplorer.databinding.ActivityMainBinding;
import com.kimikevin.eatsplorer.model.entity.Onboarding;
import com.kimikevin.eatsplorer.view.RegisterActivity;
import com.kimikevin.eatsplorer.view.adapter.OnboardingAdapter;
import com.kimikevin.eatsplorer.view.anim.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    List<Onboarding> onboardings;

    ViewPager2 onboardingViewPager;
    OnboardingAdapter onboardingAdapter;

    Button nextBtn, skipBtn;
    LinearLayout onboardingIndicators;
    ActivityMainBinding binding;
    public static final String LOG_TAG = MainActivity.class.getSimpleName().toLowerCase(Locale.ROOT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_Eatsplorer);
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

//        // Inside your activity (if you did not enable transitions in your theme)
//        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//
//        // Set an exit transition
//        getWindow().setExitTransition(new Explode());

        nextBtn = binding.nextBtn;
        skipBtn = binding.skipBtn;
        onboardingIndicators = binding.onboardingIndicators;


        setupOnboardingItems();

        // initializing the ViewPager object
        onboardingViewPager = binding.viewPager;
        onboardingViewPager.setAdapter(onboardingAdapter);
        onboardingViewPager.setPageTransformer(new ZoomOutPageTransformer());

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        skipBtn.setOnClickListener(view -> {
            if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                onboardingViewPager.setCurrentItem(onboardings.size() -1);
            } else {
                skipBtn.setEnabled(false);
            }
        });

        nextBtn.setOnClickListener(view -> {
            if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
            } else {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent
//                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                );
                finish();
            }
        });


    }

    private void setupOnboardingItems() {
        onboardings = new ArrayList<>();
        String description = "Integer a viverra sit feugiat leo\nncommodo nunc.";

        Onboarding first = new Onboarding();
        first.setTitle("Satisfy your cravings \nwith ease");
        first.setDescription(description);
        first.setImage(R.drawable.onboarding_image_1);

        Onboarding second = new Onboarding();
        second.setTitle("Find your new favourite \nrestaurant with just a tap");
        second.setDescription(description);
        second.setImage(R.drawable.onboarding_image_2);

        Onboarding third = new Onboarding();
        third.setTitle("Fresh meals, delivered to your doorstep");
        third.setDescription(description);
        third.setImage(R.drawable.onboarding_image_3);

        onboardings.add(first);
        onboardings.add(second);
        onboardings.add(third);

        onboardingAdapter = new OnboardingAdapter(onboardings, this);
    }

    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(
                    ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.default_dot
            ));
            indicators[i].setLayoutParams(params);
            onboardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicator(int index) {
        int childCount = onboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) onboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.selected_dot
                        )
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.default_dot
                        )
                );
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1) {
            nextBtn.setText(R.string.get_started);
        } else {
            nextBtn.setText(getString(R.string.next));
        }
    }

}