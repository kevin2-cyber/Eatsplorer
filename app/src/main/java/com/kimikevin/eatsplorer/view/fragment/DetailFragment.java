package com.kimikevin.eatsplorer.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.FragmentDetailBinding;
import com.kimikevin.eatsplorer.model.entity.PlaceDetailsResponse;
import com.kimikevin.eatsplorer.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private DetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        // Get placeId from Safe Args
        String placeId = DetailFragmentArgs.fromBundle(getArguments()).getPlaceId();

        if (placeId != null) {
            viewModel.fetchRestaurantDetails(placeId);
        }

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.details.observe(getViewLifecycleOwner(), details -> {
            if (details != null) {
                updateUI(details);
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            binding.pbDetail.setVisibility(isLoading ? View.VISIBLE : View.GONE);
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

        // Note: Name and Category are usually passed from the previous screen or fetched separately.
        // For now, we focus on the details fetched by this ViewModel.
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
