package com.kimikevin.eatsplorer.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.FragmentDetailBinding;
import com.kimikevin.eatsplorer.model.entity.PlaceDetailsResponse;
import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private DetailViewModel viewModel;
    private Restaurant restaurant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Apply window insets to toolbar to account for status bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar, (v, insets) -> {
            int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
            v.setPadding(0, statusBarHeight, 0, 0);
            return insets;
        });

        binding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        // Get Restaurant from Safe Args
        restaurant = DetailFragmentArgs.fromBundle(getArguments()).getRestaurant();

        if (restaurant != null) {
            setupInitialUI();
            viewModel.fetchRestaurantDetails(restaurant.id());
        }

        observeViewModel();
    }

    private void setupInitialUI() {
        binding.tvDetailName.setText(restaurant.name());
        binding.tvDetailCategory.setText(restaurant.category());
        binding.tvDetailAddress.setText(restaurant.address());

        if (restaurant.photoRef() != null && !restaurant.photoRef().isEmpty()) {
            binding.pbDetail.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(restaurant.photoRef())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            binding.pbDetail.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.pbDetail.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(binding.ivRestaurantDetail);
        }
    }

    private void observeViewModel() {
        viewModel.details.observe(getViewLifecycleOwner(), details -> {
            if (details != null) {
                updateUI(details);
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            // Keep progress bar if image is still loading, otherwise use ViewModel state
            if (Boolean.TRUE.equals(isLoading)) {
                binding.pbDetail.setVisibility(View.VISIBLE);
            } else {
                // If not loading data, hide unless image is still pending (handled by Glide listener)
                // Actually, if data loading is done, we can hide it if no photo exists
                if (restaurant.photoRef() == null || restaurant.photoRef().isEmpty()) {
                    binding.pbDetail.setVisibility(View.GONE);
                }
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(PlaceDetailsResponse details) {
        binding.tvDetailPhone.setText(details.getNationalPhoneNumber() != null ? 
                details.getNationalPhoneNumber() : "No phone number available");
        
        binding.tvDetailWebsite.setText(details.getWebsiteUri() != null ? 
                details.getWebsiteUri() : "No website available");
        
        binding.tvDetailStatus.setText(details.isOpenNow() ? "Open Now" : "Closed");
        binding.tvDetailStatus.setTextColor(getResources().getColor(details.isOpenNow() ? 
                android.R.color.holo_green_dark : android.R.color.holo_red_dark, null));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
