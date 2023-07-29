package com.kimikevin.eatsplorer.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.model.Onboarding;
import com.kimikevin.eatsplorer.view.RegisterActivity;

import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {
    Context context;

    Onboarding[] onboarding;

    LayoutInflater mLayoutInflater;

    public ViewPagerAdapter (Context context, Onboarding[] onboarding) {
        this.context = context;
        this.onboarding = onboarding;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return onboarding.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // inflating the onboarding_item.xml
        View itemView = mLayoutInflater.inflate(R.layout.onboarding_item, container, false);
        // referencing the views from the onboarding_item.xml
        ImageView imageView = itemView.findViewById(R.id.imageView);
        TextView tvTitle = itemView.findViewById(R.id.tv_onboarding_title);
        TextView tvTitleBlack = itemView.findViewById(R.id.tv_onboarding_title_black);
        TextView tvTitleRed = itemView.findViewById(R.id.tv_onboarding_title_red);
        Button getStartedBtn = itemView.findViewById(R.id.get_started_btn);
        Button skipBtn = itemView.findViewById(R.id.tv_skip);
        Button nextBtn = itemView.findViewById(R.id.next_btn);
        LinearLayout linearLayout = itemView.findViewById(R.id.ll_buttons);
        // setting the image in the ImageView
        imageView.setImageResource(onboarding[position].getImage());
        // setting the titles in the TextViews
        tvTitle.setText(onboarding[position].getTitle());
        tvTitleRed.setText(onboarding[position].getTitleRed());
        tvTitleBlack.setText(onboarding[position].getTitleBlack());
        // setting the buttons according to position
        linearLayout.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
        skipBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background));
        skipBtn.setOnClickListener(view -> skipBtn.performClick());
        nextBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background));
        getStartedBtn.setVisibility(position == 2 ? View.VISIBLE : View.INVISIBLE);
        getStartedBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, RegisterActivity.class);
            context.startActivity(intent);
        });
        // Adding the view
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
