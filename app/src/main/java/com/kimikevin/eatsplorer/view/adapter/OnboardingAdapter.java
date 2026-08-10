package com.kimikevin.eatsplorer.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.model.entity.Onboarding;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {
    private final List<Onboarding> onboardings;

    public OnboardingAdapter(List<Onboarding> onboardings) {
        this.onboardings = onboardings;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onboarding_item, parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.bind(onboardings.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardings.size();
    }

    public static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageOnboarding;
        private final TextView tvTitle;
        private final TextView tvDescription;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageOnboarding = itemView.findViewById(R.id.imageView);
            tvTitle = itemView.findViewById(R.id.tv_onboarding_title);
            tvDescription = itemView.findViewById(R.id.tv_onboarding_body);
        }

        public void bind(Onboarding onboarding) {
            tvTitle.setText(onboarding.getTitle());
            tvDescription.setText(onboarding.getDescription());
            Glide.with(imageOnboarding)
                    .load(onboarding.getImage())
                    .into(imageOnboarding);
        }
    }
}
