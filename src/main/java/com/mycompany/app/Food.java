package com.mycompany.app;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

// name -> arrayList;
class Food extends Item {
    private int satiety;
    private int moodAdd;

    public Food(String name, int money, int satiety, int moodAdd, String description) {
        super(name, money, null, description, 1); // isFood = 1
        this.satiety = satiety;
        this.moodAdd = moodAdd;
    }

    public static List<Item> getDefaultFoods() {
        List<Item> foods = new ArrayList<>();
        foods.add(new Food("狗粮", 50, 20, 0, "发狗粮啦"));
        foods.add(new Food("猫粮", 50, 20, 0, "健康又美味，干净又卫生"));
        foods.add(new Food("猫条", 100, 10, 10, "给我买给我买嘛"));
        foods.add(new Food("干草", 50, 20, 0, "富含蛋白质和钙，龙猫的最爱"));
        foods.add(new Food("水果", 50, 20, 0, "富含维生素，小鸟的最爱"));
        return foods;
    }

    public Pane create() {
        StackPane foodPane = new StackPane();
        foodPane.setPrefSize(100, 100);

        try {
            String imagePath = getImagePathByName(name);
            Image foodImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(foodImage);

            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

            Button foodButton = new Button();
            foodButton.setGraphic(imageView);
            foodButton.setStyle("-fx-background-color: transparent;");
            foodButton.setCursor(Cursor.HAND);

            foodButton.setOnMouseClicked(this::handleToyClick);

            foodPane.getChildren().add(foodButton);
        } catch (Exception e) {
            System.out.println("c si ning de meng");
        }

        return foodPane;
    }

    private String getImagePathByName(String foodName) {
        switch (foodName) {
            case "干草":
                return "/foods/hay.png";
            case "狗粮":
                return "/foods/dogFood.png";
            case "猫条":
                return "/foods/catStrips.png";
            case "猫粮":
                return "/foods/catFood.png";
            case "水果":
                return "/foods/fruit.png";
            case "饲料":
                return "/foods/fodder.png";
            default:
                return "/foods/fruit.png";
        }
    }

    @Override
    public String getImagePath() {
        return getImagePathByName(name);
    }

    private void handleToyClick(MouseEvent event) {
        System.out.println("吃了: " + name);
        System.out.println("描述: " + description);
        System.out.println("心情值增加: " + moodAdd);

    }

    public int getSatiety() {
        return satiety;
    }

    public String getDescription() {
        return description;
    }
}
