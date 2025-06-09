package com.mycompany.app;

public class ItemStack {
    private final Item item;
    private int number;

    public ItemStack(Item item, int number) {
        this.item = item;
        this.number = number;
    }

    public Item getItem() {
        return item;
    }

    public int getNumber() {
        return number;
    }

    public void addNumber(int delta) {
        this.number += delta;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return item.getName() + " ×" + number;
    }
}
