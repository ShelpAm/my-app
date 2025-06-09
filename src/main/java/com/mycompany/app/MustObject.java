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

public class MustObject extends Item {

    @Override
    public String getImagePath() {
        return getImagePathByName(name);
    }

    public MustObject(String name, int price, String description) {
        super(name, price, null, description, 0);
    }

    public static List<Item> getDefaultMustObjects() {
        List<Item> mustObjects = new ArrayList<>();
        mustObjects.add(new MustObject("狗绳", 50, "文明养狗"));
        mustObjects.add(new MustObject("猫砂", 50, "猫有三急"));
        mustObjects.add(new MustObject("宠物窝", 100, "个小家伙都需要一个温暖的小窝"));
        mustObjects.add(new MustObject("鱼缸", 100, "一方小小的海洋"));
        mustObjects.add(new MustObject("鸟笼", 100, "笼中鸟，懒得飞")); // default fallback
        return mustObjects;
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
                return "/mustObjects/dogLeash.png";
            case "猫砂":
                return "/cat-litter.png";
            case "宠物窝":
                return "/wo.png";
            case "鱼缸":
                return "/scenes/fishtank.png";
            case "鸟笼":
                return "/scenes/birdlong.png";
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

}
