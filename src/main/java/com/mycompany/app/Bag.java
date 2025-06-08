package com.mycompany.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Bag
 */
public class Bag {

    ObservableMap<String, ItemStack> content = FXCollections.observableHashMap();

    public void add(Item item, int number) {
        int originalNumber = 0;
        if (content.containsKey(item.name())) {
            originalNumber = content.remove(item.name()).getNumber();
        }
        content.put(item.name(), new ItemStack(item, originalNumber + number));
    }

    public void add(ItemStack itemStack) {
        add(itemStack.getItem(), itemStack.getNumber());
    }

}
