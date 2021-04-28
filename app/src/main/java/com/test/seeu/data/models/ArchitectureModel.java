package com.test.seeu.data.models;

public class ArchitectureModel {

    private String name;
    private String photo;
    private String author;
    private String previewInfo;
    private String mainInfo;

    public ArchitectureModel() {
    }

    public ArchitectureModel(String name, String photo, String author, String previewInfo, String mainInfo) {
        this.name = name;
        this.photo = photo;
        this.author = author;
        this.previewInfo = previewInfo;
        this.mainInfo = mainInfo;
    }

    public String getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(String mainInfo) {
        this.mainInfo = mainInfo;
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

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPreviewInfo() {
        return previewInfo;
    }

    public void setPreviewInfo(String previewInfo) {
        this.previewInfo = previewInfo;
    }
}
