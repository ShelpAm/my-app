package com.mycompany.app;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

// name -> arrayList;
class Food {
    private String name;
    private int money;
    private int satiety;
    private String description;
    private int moodAdd;

    public Food(String name) {
        this.name = name;
        if (name == "狗粮") {
            money = 50;
            satiety = 20;
            moodAdd = 0;
            description = "发狗粮啦";
        }
        else if (name == "猫粮") {
            money = 50;
            satiety = 20;
            moodAdd = 0;
            description = "健康又美味，干净又卫生";
        }
        else if (name == "猫条") {
            money = 100;
            satiety = 10;
            moodAdd = 10;
            description = "给我买给我买嘛";
        }
        else if (name == "干草") {
            money = 50;
            satiety = 20;
            moodAdd = 0;
            description = "富含蛋白质和钙，龙猫的最爱";
        }
        else if (name == "水果") {
            money = 50;
            satiety = 20;
            moodAdd = 0;
            description = "富含维生素，小鸟的最爱";
        }
        else {
            money = 50;
            satiety = 20;
            moodAdd = 0;
            description = "有7秒的记忆让我忘记有没有干过饭了";
        }
    }

    public Food() {

    };

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
        } catch(Exception  e) {
            System.out.println("c si ning de meng");
        }
        
        return foodPane;
    }
    
    private String getImagePathByName(String foodName) {
        switch(foodName) {
            case "干草":
                return "/hay.png";
            case "狗粮":
                return "/dogFood.png";
            case "猫条":
                return "/catStrips.png";
            case "猫粮":
                return "/catFood.png";
            case "水果":
                return "/fruit.png";
            case "饲料":
                return "/fodder.png";
            default:
                return "/fruit.png";
        }
    }
 
    private void handleToyClick(MouseEvent event) {
        System.out.println("吃了: " + name);
        System.out.println("描述: " + description);
        System.out.println("心情值增加: " + moodAdd);
        
    }

    public String getName() {
        return name;
    }
    
    public int getMoney() {
        return money;
    }
    
    public int getSatiety() {
        return satiety;
    }
    
    public String getDescription() {
        return description;
    }
}
