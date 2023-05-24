package com.example.nasaimagesearch.models;

import java.util.List;

public class Items {
    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    private List<Data> data;

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }

    private List<Links> links;
}
