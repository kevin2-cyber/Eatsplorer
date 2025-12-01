package com.kimikevin.eatsplorer.view.adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.kimikevin.eatsplorer.BuildConfig;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.RestaurantItemBinding;
import com.kimikevin.eatsplorer.model.entity.Restaurant;

public class RestaurantAdapter extends ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder> {
    private final Context context;
    private final OnItemClickListener listener;

    private static final String API_KEY = BuildConfig.GMP_KEY;

    public interface OnItemClickListener {
        void onItemClick(Restaurant restaurant);
    }

    private static final DiffUtil.ItemCallback<Restaurant> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getRating() == newItem.getRating();
        }
    };

    public RestaurantAdapter(Context context, OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RestaurantItemBinding binding = RestaurantItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new RestaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final RestaurantItemBinding binding;

        public RestaurantViewHolder(@NonNull RestaurantItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(getItem(position));
                }
            });
        }

        public void bind(Restaurant restaurant) {
            binding.tvRestaurantName.setText(restaurant.getName());
            binding.tvCategory.setText(restaurant.getCategory());

            // set rating and badge visibility based on the restaurant's rating
            if (restaurant.getRating() > 0) {
                binding.tvRating.setVisibility(VISIBLE);
                binding.tvRating.setText(String.valueOf(restaurant.getRating()));

                if (restaurant.getRating() >= 4.0) {
                    binding.tvRating.setBackgroundResource(R.drawable.bg_rating_badge);
                } else {
                    binding.tvRating.setBackgroundResource(R.drawable.bg_rating_badge_other);
                }
            } else {
                binding.tvRating.setVisibility(GONE);
            }

            // load image using Glide
            if(restaurant.getPhotoRef() != null) {
                String imageUrl = "https://places.googleapis.com/v1/"
                        + restaurant.getPhotoRef()
                        + "/media?maxHeightPx=400&maxWidthPx=400&key="
                        + API_KEY;

                Glide.with(context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(android.R.color.darker_gray)
                        .error(android.R.color.darker_gray)
                        .centerCrop()
                        .into(binding.ivRestaurantImage);
            } else {
                binding.ivRestaurantImage.setImageResource(android.R.color.darker_gray);
            }
        }

    }
}
