package com.kimikevin.eatsplorer.model.entity;

public class Onboarding {
    private String title;
    private int image;
    private String description;

    public Onboarding(String title, int image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
    }

    public Onboarding(){}

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
}
