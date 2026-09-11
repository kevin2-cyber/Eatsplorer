package com.kimikevin.eatsplorer.view.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import androidx.viewpager2.widget.ViewPager2;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kimikevin.eatsplorer.MainActivity;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.FragmentOnboardingBinding;
import com.kimikevin.eatsplorer.model.entity.Onboarding;
import com.kimikevin.eatsplorer.view.adapter.OnboardingAdapter;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class OnboardingFragment extends Fragment {
    private FragmentOnboardingBinding binding;
    List<Onboarding> onboardings;
    ViewPager2 onboardingPager;
    OnboardingAdapter onboardingAdapter;
    Button nextBtn, skipBtn;
    DotsIndicator onboardingIndicators;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextBtn = binding.nextBtn;
        skipBtn = binding.skipBtn;
        onboardingIndicators = binding.onboardingIndicators;
        onboardingPager = binding.viewPager;

        setupOnboardingItems();

        onboardingPager.setOffscreenPageLimit(onboardings.size() - 1);
        onboardingPager.setAdapter(onboardingAdapter);
        onboardingIndicators.attachTo(onboardingPager);

        onboardingPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                boolean isLastPage = (position == onboardingAdapter.getItemCount() - 1);
                updateNavigationButtons(isLastPage);
            }
        });

        skipBtn.setOnClickListener(skipBtnView -> {
            if(onboardingPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                onboardingPager.setCurrentItem(onboardings.size() -1);
            }
        });

        nextBtn.setOnClickListener(nextBtnView -> {
            if(onboardingPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                onboardingPager.setCurrentItem(onboardingPager.getCurrentItem() + 1);
            } else {
                requireContext().getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
                        .edit()
                        .putBoolean(MainActivity.KEY_ONBOARDING_COMPLETE, true)
                        .apply();
                
                NavDirections action = OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment();
                Navigation.findNavController(nextBtnView).navigate(action);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupOnboardingItems() {
        onboardings = new ArrayList<>();

        Onboarding first = new Onboarding();
        first.setTitle("Discover restaurants you'll love");
        first.setDescription("Browse hundreds of local restaurants, read real reviews, and find your next favourite spot — all in one place.");
        first.setImage(R.drawable.adrien);

        Onboarding second = new Onboarding();
        second.setTitle("Order in seconds, not minutes");
        second.setDescription("A few taps and your meal is on its way. Track your order in real time from kitchen to door.");
        second.setImage(R.drawable.volkan);

        Onboarding third = new Onboarding();
        third.setTitle("Fresh food, at your door");
        third.setDescription("Hot, fresh meals delivered fast. Because great food shouldn't keep you waiting.");
        third.setImage(R.drawable.kayleigh);

        onboardings.add(first);
        onboardings.add(second);
        onboardings.add(third);

        onboardingAdapter = new OnboardingAdapter(onboardings);
    }

    private void updateNavigationButtons(boolean isLastPage) {
        // Tell Android to automatically animate all layout bounds and visibility changes
        AutoTransition transition = new AutoTransition();
        transition.setDuration(300); // 300ms is standard for UI movement
        TransitionManager.beginDelayedTransition(binding.llButtons, transition);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.nextBtn.getLayoutParams();

        // Define the target colors based on the state
        int targetBgColor = isLastPage ? ContextCompat.getColor(requireContext(), R.color.white) : Color.TRANSPARENT;
        int targetTextColor = isLastPage ? Color.BLACK : ContextCompat.getColor(requireContext(), R.color.white);

        // Grab the current colors so the animation starts exactly where the user is
        int currentBgColor = binding.nextBtn.getBackgroundTintList() != null
                ? binding.nextBtn.getBackgroundTintList().getDefaultColor()
                : Color.TRANSPARENT;
        int currentTextColor = binding.nextBtn.getCurrentTextColor();

        if (isLastPage) {
            binding.skipBtn.setVisibility(View.GONE);
            binding.nextBtn.setText(R.string.get_started);

            params.setMarginStart(0);

            params.width = (int) (280 * getResources().getDisplayMetrics().density);

            binding.nextBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
            binding.nextBtn.setTextColor(Color.BLACK);
            binding.nextBtn.setStrokeWidth(0);
            binding.nextBtn.setTextAppearance(R.style.TextAppearance_Eatsplorer_Onboarding_Solid);
        } else {
            binding.skipBtn.setVisibility(View.VISIBLE);
            binding.nextBtn.setText(R.string.next);

            // Restore original 40dp margin and 140dp width when swiping back
            params.setMarginStart((int) (40 * getResources().getDisplayMetrics().density));
            params.width = (int) (140 * getResources().getDisplayMetrics().density);

            binding.nextBtn.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
            binding.nextBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            binding.nextBtn.setStrokeWidth((int) (1 * getResources().getDisplayMetrics().density));
            binding.nextBtn.setTextAppearance(R.style.TextAppearance_Eatsplorer_Onboarding_Outlined);
        }

        binding.nextBtn.setLayoutParams(params);

        ValueAnimator colorAnimator = ValueAnimator.ofArgb(currentBgColor, targetBgColor);
        colorAnimator.setDuration(300);
        colorAnimator.addUpdateListener(animator -> binding.nextBtn.setBackgroundTintList(ColorStateList.valueOf((int) animator.getAnimatedValue())));
        colorAnimator.start();

        ValueAnimator textAnimator = ValueAnimator.ofArgb(currentTextColor, targetTextColor);
        textAnimator.setDuration(300);
        textAnimator.addUpdateListener(animator -> binding.nextBtn.setTextColor((int) animator.getAnimatedValue()));
        textAnimator.start();
    }
}