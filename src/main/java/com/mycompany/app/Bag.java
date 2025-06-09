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
        if (content.containsKey(item.getName())) {
            originalNumber = content.remove(item.getName()).getNumber();
        }
        content.put(item.getName(), new ItemStack(item, originalNumber + number));
    }

    public void add(ItemStack itemStack) {
        add(itemStack.getItem(), itemStack.getNumber());
    }

}
