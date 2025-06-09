package com.mycompany.app;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class MustObject {
    private String name;
    private int money;
    private String description;

    public MustObject(String name) {
        this.name = name;
        if (name == "狗绳") {
            money = 50;
            description = "文明养狗";
        }
        else if (name == "猫砂") {
            money = 50;
            description = "猫有三急";
        }
        else if (name == "宠物窝") {
            money = 100;
            description = "个小家伙都需要一个温暖的小窝";
        }
        else if (name == "鱼缸") {
            money = 100;
            description = "一方小小的海洋";
        }
        else {
            money = 100;
            description = "笼中鸟，懒得飞";
        }
    }

    public MustObject() {

    }
    public Pane createUI() {
        StackPane toyPane = new StackPane();
        toyPane.setPrefSize(100, 100); 

        try {
            String imagePath = getImagePathByName(name);
            Image mbImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(mbImage);

            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

            Button mbButton = new Button();
            mbButton.setGraphic(imageView);
            mbButton.setStyle("-fx-background-color: transparent;");
            mbButton.setCursor(Cursor.HAND);

            mbButton.setOnMouseClicked(this::handleToyClick);

            toyPane.getChildren().add(mbButton);
        } catch (Exception e) {
            System.out.println("c si ning de meng");
        }

        return toyPane;
    }

    private String getImagePathByName(String mbName) {
        switch (mbName) {
            case "狗绳":
                return "/dogLeash.png";
            case "猫砂":
                return "/catLitter.png";
            case "宠物窝":
                return "/petKennels.png";
            case "鱼缸":
                return "/fishTank.png";
            case "鸟笼":
                return "/birdCage.png";
            default:
                return "/dogLeash.png";
        }
    }

    // 处理玩具点击事件
    private void handleToyClick(MouseEvent event) {
        System.out.println("买了: " + name);
        System.out.println("描述: " + description);

        // 这里可以添加实际游戏逻辑，如增加宠物心情值等
    }

    // Getter方法
    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public String getDescription() {
        return description;
    }
}
