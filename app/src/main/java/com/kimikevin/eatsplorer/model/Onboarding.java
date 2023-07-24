package com.kimikevin.eatsplorer.model;

public class Onboarding {
    private String title;
    private int image;
    private String titleBlack;
    private String titleRed;

    public Onboarding(String title, int image, String titleBlack, String titleRed) {
        this.title = title;
        this.image = image;
        this.titleBlack = titleBlack;
        this.titleRed = titleRed;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getTitleBlack() {
        return titleBlack;
    }

    public String getTitleRed() {
        return titleRed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTitleBlack(String titleBlack) {
        this.titleBlack = titleBlack;
    }

    public void setTitleRed(String titleRed) {
        this.titleRed = titleRed;
    }
}
