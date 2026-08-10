package com.kimikevin.eatsplorer.view.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        setupOnboardingItems();

        onboardingPager = binding.viewPager;
        onboardingPager.setOffscreenPageLimit(onboardings.size() - 1);
        onboardingPager.setAdapter(onboardingAdapter);
        onboardingIndicators.attachTo(onboardingPager);

        onboardingPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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

        skipBtn.setOnClickListener(skipBtnView -> {
            if(onboardingPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                onboardingPager.setCurrentItem(onboardings.size() -1);
            } else {
                skipBtn.setEnabled(false);
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
}