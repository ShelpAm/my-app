package com.mycompany.app;

import java.util.ArrayList;

public class Market {

    private ArrayList<Item> items = new ArrayList<>();

    public Market() {
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String toString() {
        return items.toString();
    }

}
