package com.mycompany.app;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PopupButtonHelper {

    public static void showPopupButtons(Pane parent, double x, double y,
            String[] imagePaths,
            Runnable action1,
            Runnable action2,
            Runnable action3) {
        if (imagePaths.length < 3)
            throw new IllegalArgumentException("需要 3 个按钮图片");

        Runnable[] actions = { action1, action2, action3 };

        double startX = x; // 相对 Pane 的坐标
        double startY = y;

        for (int i = 0; i < 3; i++) {
            ImageView view = new ImageView(new Image(PopupButtonHelper.class.getResourceAsStream(imagePaths[i])));
            view.setFitWidth(50);
            view.setFitHeight(50);
            Circle clip = new Circle(25, 25, 25);
            view.setClip(clip);

            Button btn = new Button();
            btn.setGraphic(view);
            btn.setStyle("-fx-background-color: transparent;");
            btn.setPrefSize(50, 50);
            btn.setLayoutX(startX);
            btn.setLayoutY(startY);
            btn.setOpacity(0);

            int index = i;
            btn.setOnAction(ev -> {
                actions[index].run();
                parent.getChildren().remove(btn); // 可选：点击后移除按钮
            });

            parent.getChildren().add(btn);

            TranslateTransition slide = new TranslateTransition(Duration.millis(150), btn);
            slide.setFromX(0);
            slide.setToX(70 * (i - 1)); // -70, 0, +70 横向排列
            slide.setDelay(Duration.millis(i * 80));

            FadeTransition fade = new FadeTransition(Duration.millis(150), btn);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setDelay(Duration.millis(i * 80));

            new ParallelTransition(slide, fade).play();
        }
    }
}
