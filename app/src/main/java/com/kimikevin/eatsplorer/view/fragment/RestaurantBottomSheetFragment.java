package com.kimikevin.eatsplorer.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.BottomSheetRestaurantBinding;
import com.kimikevin.eatsplorer.model.entity.Restaurant;

public class RestaurantBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_RESTAURANT = "restaurant";
    private BottomSheetRestaurantBinding binding;

    public static RestaurantBottomSheetFragment newInstance(Restaurant restaurant) {
        RestaurantBottomSheetFragment fragment = new RestaurantBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = BottomSheetRestaurantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) return;
        Restaurant restaurant = (Restaurant) getArguments().getSerializable(ARG_RESTAURANT);
        if (restaurant == null) return;

        binding.tvSheetName.setText(restaurant.name());
        binding.tvSheetCategoryRating.setText(
                getString(R.string.rating_format, restaurant.rating()) + "  •  " + restaurant.category());
        binding.tvSheetAddress.setText(restaurant.address());

        Glide.with(binding.ivSheetPhoto)
                .load(restaurant.photoRef())
                .into(binding.ivSheetPhoto);

        binding.btnViewDetails.setOnClickListener(v -> {
            dismiss();
            MapFragmentDirections.ActionMapFragmentToDetailFragment action =
                    MapFragmentDirections.actionMapFragmentToDetailFragment(restaurant);
            Navigation.findNavController(requireParentFragment().requireView()).navigate(action);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
