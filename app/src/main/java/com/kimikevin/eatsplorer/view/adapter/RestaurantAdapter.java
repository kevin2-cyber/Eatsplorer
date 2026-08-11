package com.kimikevin.eatsplorer.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.ItemRestaurantBinding;
import com.kimikevin.eatsplorer.model.entity.Restaurant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurants = new ArrayList<>();
    private Set<String> favoriteIds = new HashSet<>();
    private final OnRestaurantClickListener clickListener;
    private final OnFavoriteClickListener favoriteListener;

    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Restaurant restaurant);
    }

    public RestaurantAdapter(OnRestaurantClickListener clickListener, OnFavoriteClickListener favoriteListener) {
        this.clickListener = clickListener;
        this.favoriteListener = favoriteListener;
    }

    public RestaurantAdapter(OnRestaurantClickListener clickListener) {
        this(clickListener, null);
    }

    public void setRestaurants(List<Restaurant> newList) {
        DiffUtil.DiffResult diff = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override public int getOldListSize() { return restaurants.size(); }
            @Override public int getNewListSize() { return newList.size(); }
            @Override public boolean areItemsTheSame(int o, int n) {
                return restaurants.get(o).id().equals(newList.get(n).id());
            }
            @Override public boolean areContentsTheSame(int o, int n) {
                return restaurants.get(o).equals(newList.get(n));
            }
        });
        restaurants = newList;
        diff.dispatchUpdatesTo(this);
    }

    public void setFavoriteIds(Set<String> ids) {
        favoriteIds = ids;
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
        holder.bind(restaurants.get(position), favoriteIds, clickListener, favoriteListener);
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

        public void bind(Restaurant restaurant, Set<String> favoriteIds,
                         OnRestaurantClickListener clickListener,
                         OnFavoriteClickListener favoriteListener) {
            binding.tvRestaurantName.setText(restaurant.name());
            binding.tvRestaurantCategory.setText(restaurant.category());
            binding.tvRestaurantRating.setText(
                    itemView.getContext().getString(R.string.rating_format, restaurant.rating()));

            Glide.with(binding.ivRestaurant)
                    .load(restaurant.photoRef())
                    .into(binding.ivRestaurant);

            boolean isFav = favoriteIds.contains(restaurant.id());
            binding.ibFavorite.setImageResource(isFav ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);

            binding.ibFavorite.setOnClickListener(v -> {
                if (favoriteListener != null) favoriteListener.onFavoriteClick(restaurant);
            });

            binding.cardRestaurant.setOnClickListener(v -> {
                if (clickListener != null) clickListener.onRestaurantClick(restaurant);
            });
        }
    }
}
