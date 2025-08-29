package com.kimikevin.eatsplorer.model.entity;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Onboarding {
    private String title;
    private int image;
    private String description;

    public Onboarding(){

    }

    public Onboarding(String title, int image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return "Onboarding{" +
                "title='" + title + '\'' +
                ", image=" + image +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Onboarding onboarding = (Onboarding) o;
        return image == onboarding.image
                && Objects.equals(title, onboarding.title)
                && Objects.equals(description, onboarding.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, image, description);
    }
}
