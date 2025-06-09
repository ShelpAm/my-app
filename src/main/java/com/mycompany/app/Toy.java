package com.mycompany.app;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class Toy {
    public int current;
    private String name;
    private int money;
    private int moodAdd;
    private String description;

    // 创建玩具的UI组件

    public Pane createUI() {
        // 创建根Pane
        StackPane toyPane = new StackPane();
        toyPane.setPrefSize(100, 100); // 设置默认大小

        // 根据玩具名称加载对应图片
        try {
            String imagePath = getImagePathByName(name);
            Image toyImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(toyImage);

            // 设置图片大小
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

            // 创建透明按钮覆盖在图片上
            Button toyButton = new Button();
            toyButton.setGraphic(imageView);
            toyButton.setStyle("-fx-background-color: transparent;");
            toyButton.setCursor(Cursor.HAND);

            // 添加点击事件
            toyButton.setOnMouseClicked(this::handleToyClick);

            // 将按钮添加到Pane中
            toyPane.getChildren().add(toyButton);
        } catch (Exception e) {
            System.out.println("c si ning de meng");
        }

        return toyPane;
    }

    // 根据玩具名称获取图片路径
    private String getImagePathByName(String toyName) {
        // 这里可以根据实际项目结构调整图片路径
        switch (toyName) {
            case "飞盘":
                return "/飞盘.png";
            case "逗猫棒":
                return "/逗猫棒.png";
            case "跑轮":
                return "/跑轮.png";
            case "狗绳":
                return "/狗绳.png";
            // 添加更多玩具类型...
            default:
                return "/飞盘.png";
        }
    }

    // 处理玩具点击事件
    private void handleToyClick(MouseEvent event) {
        System.out.println("使用了玩具: " + name);
        System.out.println("描述: " + description);
        System.out.println("心情值增加: " + moodAdd);

        // 这里可以添加实际游戏逻辑，如增加宠物心情值等
    }

    // Getter方法
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
