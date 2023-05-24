package com.example.nasaimagesearch.models;

import java.util.List;

public class Collection {
    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    private List<Items> items;
}
