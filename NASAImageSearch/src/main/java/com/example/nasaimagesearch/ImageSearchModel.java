package com.example.nasaimagesearch;

public class ImageSearchModel {
    public String getNasaId() {
        return nasaId;
    }

    public void setNasaId(String nasaId) {
        this.nasaId = nasaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private String nasaId;
    private String title;
    private  String description;
    private String dateCreated;
    private String link;

    ImageSearchModel(String nasaId, String title, String description, String dateCreated, String link) {
        this.nasaId = nasaId;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.link = link;
    }
}
