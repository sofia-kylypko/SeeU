package com.test.seeu.ArchitectureFragment;

public class ArchitectureModel {

    private String name;
    private String imageURL;
    private String author;
    private String details;

    public ArchitectureModel(String name, String imageURL, String author, String details) {
        this.name = name;
        this.imageURL = imageURL;
        this.author = author;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
