package com.mycompany.app;

public class Item {
    protected String name;
    protected int price;
    protected String imagePath;
    protected String description;
    protected int isFood;

    public Item(String name, int price, String imagePath, String description, int isFood) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.description = description;
        this.isFood = isFood;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    public int getIsFood() {
        return isFood;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                ", description='" + description + '\'' +
                ", isFood=" + isFood +
                '}';
    }
}
