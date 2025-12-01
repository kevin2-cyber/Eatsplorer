package com.kimikevin.eatsplorer.view;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.ActivityOnboardingBinding;
import com.kimikevin.eatsplorer.model.entity.Onboarding;
import com.kimikevin.eatsplorer.view.adapter.OnboardingAdapter;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class OnboardingActivity extends AppCompatActivity {
    List<Onboarding> onboardings;

    ViewPager2 onboardingViewPager;
    OnboardingAdapter onboardingAdapter;

    Button nextBtn, skipBtn;
    DotsIndicator onboardingIndicators;
    FirebaseAuth auth;

    ActivityOnboardingBinding binding;
    public static final String LOG_TAG = OnboardingActivity.class.getSimpleName().toLowerCase(Locale.ROOT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding);

        // init auth
        auth = FirebaseAuth.getInstance();
        checkUser();

        nextBtn = binding.nextBtn;
        skipBtn = binding.skipBtn;
        onboardingIndicators = binding.onboardingIndicators;


        setupOnboardingItems();

        // initializing the ViewPager object
        onboardingViewPager = binding.viewPager;
        onboardingViewPager.setAdapter(onboardingAdapter);
        onboardingIndicators.attachTo(onboardingViewPager);


        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == onboardingAdapter.getItemCount() -1) {
                    nextBtn.setText(R.string.get_started);
                } else {
                    nextBtn.setText(getString(R.string.next));
                }
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
                startActivity(intent);
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

    private void checkUser() {
        // check if user is logged in or not
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {

            // user is not null, user is logged in, get user info
            String email = user.getEmail();

            // set to text view
            Log.d(LOG_TAG, " You are logged in as " + email);
            Toast.makeText(this, "You are logged in as " + email, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MapsActivity.class));
            finish();
        } else {

            //user is null, user not logged in go to login activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}