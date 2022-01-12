package com.test.seeu.data.models;

public class ArchitectureModel {

    private String name, photo, author, previewInfo, mainInfo;

    public ArchitectureModel() { }

    public String getMainInfo() {
        return mainInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAuthor() {
        return author;
    }

    public String getPreviewInfo() {
        return previewInfo;
    }
}