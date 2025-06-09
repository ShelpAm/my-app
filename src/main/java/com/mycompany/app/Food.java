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
    private ArrayList<String> adapt;

    public Food(String name, int money, int satiety, int moodAdd, String description) {
        this.name = name;
        this.money = money;
        this.satiety = satiety;
        this.moodAdd = moodAdd;
        this.description = description;
        // this.adapt = getArrayList(name);
    }

    public Food() {

    };

        // 创建玩具的UI组件
    public Pane create() {
        // 创建根Pane
        StackPane foodPane = new StackPane();
        foodPane.setPrefSize(100, 100); // 设置默认大小
        
        // 根据玩具名称加载对应图片
        try {
            String imagePath = getImagePathByName(name);
            Image foodImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(foodImage);
            
            // 设置图片大小
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);
            
            // 创建透明按钮覆盖在图片上
            Button foodButton = new Button();
            foodButton.setGraphic(imageView);
            foodButton.setStyle("-fx-background-color: transparent;");
            foodButton.setCursor(Cursor.HAND);
            
            // 添加点击事件
            foodButton.setOnMouseClicked(this::handleToyClick);
            
            // 将按钮添加到Pane中
            foodPane.getChildren().add(foodButton);
        } catch(Exception  e) {
            System.out.println("c si ning de meng");
        }
        
        return foodPane;
    }
    
    // 根据玩具名称获取图片路径
    private String getImagePathByName(String foodName) {
        // 这里可以根据实际项目结构调整图片路径
        switch(foodName) {
            case "飞盘":
                return "/1.png";
            case "逗猫棒":
                return "/1.png";
            case "跑轮":
                return "/1.png";
            // 添加更多玩具类型...
            default:
                return "/1.png";
        }
    }
        // 处理玩具点击事件
    private void handleToyClick(MouseEvent event) {
        System.out.println("使用了玩具: " + name);
        System.out.println("描述: " + description);
        System.out.println("心情值增加: " + moodAdd);
        
        // 这里可以添加实际游戏逻辑，如增加宠物心情值等
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
