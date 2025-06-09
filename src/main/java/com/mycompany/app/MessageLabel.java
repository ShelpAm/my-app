package com.mycompany.app;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

class MessageLabel extends Label {

    private PauseTransition visibleDuration;
    private FadeTransition fadeOut;

    public MessageLabel(Pane parentPane) {
        // 基本样式设置
        setTextFill(Color.WHITE);
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); " +
                "-fx-padding: 10px 20px; " +
                "-fx-background-radius: 5px; " +
                "-fx-font-size: 14px;" +
                "-fx-text-fill: white;");

        // 初始不可见
        setVisible(false);

        // 添加到父容器并居中
        parentPane.getChildren().add(this);
        layoutXProperty().bind(parentPane.widthProperty().subtract(widthProperty()).divide(2));
        layoutYProperty().bind(parentPane.heightProperty().subtract(heightProperty()).divide(2));

        // 设置可见持续时间
        visibleDuration = new PauseTransition();
        visibleDuration.setOnFinished(event -> {
            fadeOut.play(); // 持续时间结束后开始淡出
        });

        // 设置淡出动画
        fadeOut = new FadeTransition(Duration.millis(2000), this);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> setVisible(false));
    }

    /**
     * 显示消息并在指定时间后自动消失
     * 
     * @param message  要显示的消息
     * @param duration 显示总时间(毫秒) - 包括淡出时间
     */
    public void showMessage(String message, int duration) {
        setText(message);
        setVisible(true);
        setOpacity(1.0); // 确保完全可见

        // 停止之前的动画（如果正在运行）
        visibleDuration.stop();
        fadeOut.stop();

        // 设置新的持续时间（总时间减去淡出时间）
        int fadeDuration = 2000; // 淡出动画时间
        int remainingDuration = Math.max(0, duration - fadeDuration);
        visibleDuration.setDuration(Duration.millis(remainingDuration));

        visibleDuration.play();
    }
}
