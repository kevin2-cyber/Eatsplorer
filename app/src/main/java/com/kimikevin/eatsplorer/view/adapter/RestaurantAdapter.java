package com.kimikevin.eatsplorer.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kimikevin.eatsplorer.databinding.ItemRestaurantBinding;
import com.kimikevin.eatsplorer.model.entity.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurants = new ArrayList<>();
    private final OnRestaurantClickListener listener;

    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    public RestaurantAdapter(OnRestaurantClickListener listener) {
        this.listener = listener;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRestaurantBinding binding = ItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RestaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.bind(restaurants.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final ItemRestaurantBinding binding;

        public RestaurantViewHolder(ItemRestaurantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Restaurant restaurant, OnRestaurantClickListener listener) {
            binding.tvRestaurantName.setText(restaurant.name());
            binding.tvRestaurantCategory.setText(restaurant.category());
            binding.tvRestaurantRating.setText(String.valueOf(restaurant.rating()));

            if (restaurant.photoRef() != null && !restaurant.photoRef().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(restaurant.photoRef())
                        .into(binding.ivRestaurant);
            }

            itemView.setOnClickListener(v -> listener.onRestaurantClick(restaurant));
        }
    }
}
