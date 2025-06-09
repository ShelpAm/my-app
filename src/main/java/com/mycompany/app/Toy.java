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

public class Toy {
    private String name;
    private int money;
    private String description;
    private int moodAdd;

    public static List<Toy> getDefaultToys() {
        List<Toy> toys = new ArrayList<>();
        toys.add(new Toy("飞盘"));
        toys.add(new Toy("逗猫棒"));
        toys.add(new Toy("跑轮"));
        return toys;
    }

    public Toy(String name) {
        this.name = name;
        if (name.equals("飞盘")) {
            money = 100;
            moodAdd = 10;
            description = "狗们最喜欢的玩具，可以让狗狗们尽情释放活力";
        } else if (name.equals("逗猫棒")) {
            money = 100;
            moodAdd = 10;
            description = "好奇宝宝有着无法拒绝的吸引力";
        } else if (name.equals("跑轮")) {
            money = 100;
            moodAdd = 10;
            description = "鼠鼠我呀，最喜欢跑步啦。";
        }
    }

    public Toy() {

    }

    public Pane createUI() {

        StackPane toyPane = new StackPane();
        toyPane.setPrefSize(100, 100);

        try {
            String imagePath = getImagePathByName(name);
            Image toyImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(toyImage);

            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

            Button toyButton = new Button();
            toyButton.setGraphic(imageView);
            toyButton.setStyle("-fx-background-color: transparent;");
            toyButton.setCursor(Cursor.HAND);

            toyButton.setOnMouseClicked(this::handleToyClick);

            toyPane.getChildren().add(toyButton);
        } catch (Exception e) {
            System.out.println("c si ning de meng");
        }

        return toyPane;
    }

    private String getImagePathByName(String toyName) {

        switch (toyName) {
            case "飞盘":
                return "/frisbee.png";
            case "逗猫棒":
                return "/catTeaser.png";
            case "跑轮":
                return "/runningWheels.png";
            default:
                return "/frisbee.png";
        }
    }

    private void handleToyClick(MouseEvent event) {
        System.out.println("使用了玩具: " + name);
        System.out.println("描述: " + description);
        System.out.println("心情值增加: " + moodAdd);
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public int getMoodAdd() {
        return moodAdd;
    }

    public String getDescription() {
        return description;
    }
}
