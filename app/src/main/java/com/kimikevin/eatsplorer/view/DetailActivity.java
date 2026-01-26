package com.kimikevin.eatsplorer.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.kimikevin.eatsplorer.BuildConfig;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.ActivityDetailBinding;
import com.kimikevin.eatsplorer.model.entity.PlaceDetailsResponse;
import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.viewmodel.DetailViewModel;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private DetailViewModel viewModel;
    private static final String API_KEY = BuildConfig.GMP_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.res_details), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setLifecycleOwner(this);

        setSupportActionBar(binding.detailToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false); // Hide default title
        }
        binding.detailToolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("RESTAURANT");

        if (restaurant == null) {
            Toast.makeText(this, "Error loading restaurant", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupBasicViews(restaurant);

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        setupObservers();

        viewModel.fetchRestaurantDetails(restaurant.getId());

    }

    private void setupBasicViews(Restaurant restaurant) {
        binding.tvName.setText(restaurant.getName());
        binding.tvAddress.setText(restaurant.getAddress());
        binding.tvCategory.setText(restaurant.getCategory());

        if (restaurant.getRating() > 0) {
            binding.tvRating.setText(restaurant.getRating() + " ★");
            binding.tvRating.setVisibility(VISIBLE);
        } else {
            binding.tvRating.setVisibility(GONE);
        }

        if (restaurant.getPhotoRef() != null) {
            String imageUrl = "https://places.googleapis.com/v1/" +
                    restaurant.getPhotoRef() +
                    "/media?maxHeightPx=800&maxWidthPx=800&key=" + API_KEY;

            Glide.with(this)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(android.R.color.darker_gray)
                    .centerCrop()
                    .into(binding.ivDetailImage);
        }
    }

    private void setupObservers(){
        // observe loading state
        viewModel.isLoading.observe(this, isLoading -> binding.progressBar.setVisibility(isLoading ? VISIBLE : GONE));

        // observe errors
        viewModel.errorMessage.observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                binding.contactContainer.setVisibility(GONE);
            }
        });

        // observe the contact info(phone & website)
        viewModel.details.observe(this, details -> {
            if (details != null) {
                updateContactInfo(details);
            }
        });
    }

    private void updateContactInfo(PlaceDetailsResponse details) {
        binding.contactContainer.setVisibility(VISIBLE);
        PackageManager pm = this.getPackageManager();
        boolean isPhone = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);

        if (details.nationalPhoneNumber != null || isPhone) {
            binding.btnCall.setVisibility(VISIBLE);
            binding.btnCall.setText("Call " + details.nationalPhoneNumber);

            binding.btnCall.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + details.nationalPhoneNumber));
                startActivity(intent);
            });
        } else {
            binding.btnCall.setVisibility(GONE);
        }

        if (details.websiteUri != null) {
            binding.btnWebsite.setVisibility(View.VISIBLE);

            binding.btnWebsite.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(details.websiteUri));
                startActivity(intent);
            });
        } else {
            binding.btnWebsite.setVisibility(View.GONE);
        }
    }
}