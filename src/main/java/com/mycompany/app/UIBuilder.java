package com.mycompany.app;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class UIBuilder {

    private final SceneManager sceneManager;
    private final BooleanProperty drawerOpen = new SimpleBooleanProperty(false);
    private final BooleanProperty backpackOpen = new SimpleBooleanProperty(false);

    public UIBuilder(SceneManager sm) {
        this.sceneManager = sm;
    }

    public Pane makeUI() {
        Pane ui = new Pane();
        ui.getStylesheets().add(getClass().getResource("/ui.css").toExternalForm());

        {
            // // 1) The sliding drawer
            // Pane drawer = new Pane();
            // drawer.setId("drawer");
            // drawer.applyCss();
            //
            // // Buttons inside drawer
            // Button saveBtn = new Button("Save");
            // saveBtn.setLayoutX(10);
            // saveBtn.setLayoutY(10);
            // Button exitBtn = new Button("Exit");
            // exitBtn.setLayoutX(50);
            // exitBtn.setLayoutY(10);
            // exitBtn.setOnAction(e -> {
            // // TODO: save Save
            // Platform.exit();
            // });
            // drawer.getChildren().addAll(saveBtn, exitBtn);
            //
            // // 2) The tab button
            // Button tab = new Button("≡");
            // tab.setId("tab");
            // tab.applyCss();
            //
            // // 3) Add both to root
            // ui.getChildren().addAll(drawer, tab);
            //
            // // 4) Bind drawer & tab positions to scene size
            // drawer.layoutXProperty().bind(sceneManager.getScene().widthProperty().subtract(10));
            // drawer.layoutYProperty().bind(
            // sceneManager.getScene().heightProperty().subtract(drawer.getPrefHeight() + 10
            // + 20));
            // tab.layoutXProperty().bind(
            // sceneManager.getScene().widthProperty().subtract(tab.getPrefWidth() + 10 +
            // 20));
            // tab.layoutYProperty().bind(
            // sceneManager.getScene().heightProperty().subtract(tab.getPrefHeight() + 10 +
            // 20));
            //
            // // 5) Slide animation
            // TranslateTransition slide = new TranslateTransition(Duration.millis(300),
            // drawer);
            // slide.setInterpolator(Interpolator.EASE_BOTH);
            //
            // tab.setOnAction(e -> {
            // System.out.println("Hey!");
            // if (!drawerOpen.get()) {
            // slide.setFromX(0);
            // slide.setToX(-(drawer.getPrefWidth() + 10));
            // } else {
            // slide.setFromX(-(drawer.getPrefWidth() + 10));
            // slide.setToX(0);
            // }
            // slide.play();
            // drawerOpen.set(!drawerOpen.get());
            // });

            // 1) The sliding drawer
            Pane drawer = new Pane();
            drawer.setId("drawer");
            drawer.setPrefSize(150, 100); // ✅ 必须设置，否则动画和定位计算会出错
            drawer.applyCss();

            // Buttons inside drawer
            Button saveBtn = new Button("Save");
            saveBtn.setLayoutX(10);
            saveBtn.setLayoutY(10);
            Button exitBtn = new Button("Exit");
            exitBtn.setLayoutX(60);
            exitBtn.setLayoutY(10);
            exitBtn.setOnAction(e -> {
                // TODO: save Save
                Platform.exit();
            });
            drawer.getChildren().addAll(saveBtn, exitBtn);

            // 2) The tab button
            Button tab = new Button("≡");
            tab.setId("tab");
            tab.applyCss();
            tab.setPrefSize(30, 30); // ✅ 可设定一下大小

            // 3) Add both to root
            ui.getChildren().addAll(drawer, tab);

            // 4) Bind positions (向内偏移一点，不太靠角落)
            drawer.layoutXProperty().bind(sceneManager.getScene().widthProperty().subtract(drawer.getPrefWidth() + 20));
            drawer.layoutYProperty()
                    .bind(sceneManager.getScene().heightProperty().subtract(drawer.getPrefHeight() + 60));
            tab.layoutXProperty().bind(sceneManager.getScene().widthProperty().subtract(tab.getPrefWidth() + 20));
            tab.layoutYProperty().bind(sceneManager.getScene().heightProperty().subtract(tab.getPrefHeight() + 20));

            // 5) Slide animation
            TranslateTransition slide = new TranslateTransition(Duration.millis(300), drawer);
            slide.setInterpolator(Interpolator.EASE_BOTH);

            tab.setOnAction(e -> {
                if (!drawerOpen.get()) {
                    slide.setFromX(0);
                    slide.setToX(-(drawer.getPrefWidth()));
                } else {
                    slide.setFromX(-(drawer.getPrefWidth()));
                    slide.setToX(0);
                }
                slide.play();
                drawerOpen.set(!drawerOpen.get());
            });
        }

        {
            // 1) 创建背包面板
            Pane backpackPane = new Pane();

            BackpackUI backpackUI = new BackpackUI();
            backpackPane.getChildren().add(backpackUI.getView());
            Player.getInstance().getBag().add(new Item("zyx", -1, "/s.png"), 1);
            backpackUI.refresh();

            backpackPane.setPrefSize(500, 400);
            backpackPane.setStyle(
                    "-fx-background-color: rgba(200,200,200,0.9); -fx-border-radius:8; -fx-background-radius:8;");
            // 最终位置：居中
            double finalX = (sceneManager.getScene().getWidth() - backpackPane.getPrefWidth()) / 2;
            double finalY = (sceneManager.getScene().getHeight() - backpackPane.getPrefHeight()) / 2;
            backpackPane.setLayoutX(finalX);
            backpackPane.setLayoutY(finalY);

            // 初始从左下角进入，所以用 translate 偏移
            // translateX 从 -(finalX + width)（完全在左下外）到 0
            // translateY 从 (sceneManager.getScene().height - finalY)（位于下方外）到 0
            backpackPane.setTranslateX(-(finalX + backpackPane.getPrefWidth()));
            backpackPane.setTranslateY(sceneManager.getScene().getHeight() - finalY);
            // 初始旋转 -45°
            backpackPane.setRotate(-45);

            // 2) 触发按钮
            Button backpackBtn = new ClippedImageButton("/backpack.png");
            backpackBtn.setId("backpack-button");
            backpackBtn.setLayoutX(-400);
            backpackBtn.setLayoutY(220);
            ui.getChildren().addAll(backpackPane, backpackBtn);

            // 3) 平移动画
            TranslateTransition tt = new TranslateTransition(Duration.millis(800), backpackPane);
            tt.setToX(0);
            tt.setToY(0);
            tt.setInterpolator(Interpolator.EASE_BOTH);

            // 4) 旋转动画（逆时针：-45 → 0）
            RotateTransition rt = new RotateTransition(Duration.millis(800), backpackPane);
            rt.setFromAngle(-45);
            rt.setToAngle(0);
            rt.setInterpolator(Interpolator.EASE_BOTH);

            // 5) 并行动画
            ParallelTransition spiralIn = new ParallelTransition(tt, rt);
            // 1) 创建遮罩层
            Pane maskPane = new Pane();
            maskPane.setPrefSize(sceneManager.getScene().getWidth(),
                    sceneManager.getScene().getHeight());
            maskPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);"); // 半透明黑色遮罩
            maskPane.setVisible(false); // 默认隐藏

            // 2) 遮罩层阻止所有鼠标事件传递
            maskPane.setOnMouseClicked(e -> {
                // 可以在遮罩点击时关闭背包（如果需要）
                // 或者什么都不做，只阻止事件传递
                e.consume();
            });

            // 3) 添加遮罩层到根容器，且放在背包面板的下面
            ui.getChildren().add(maskPane);

            spiralIn.setOnFinished(evt -> {
                maskPane.setVisible(true); // 显示遮罩层
                backpackPane.toFront();
                backpackBtn.toFront();
            });

            backpackBtn.setOnAction(e -> {
                if (!backpackOpen.get()) {
                    spiralIn.playFromStart();
                } else {
                    maskPane.setVisible(false); // 隐藏遮罩层
                    // 反向动画：旋转回去并移出
                    TranslateTransition ttBack = new TranslateTransition(Duration.millis(800), backpackPane);
                    ttBack.setToX(-(finalX + backpackPane.getPrefWidth()));
                    ttBack.setToY(sceneManager.getScene().getHeight() - finalY);
                    ttBack.setInterpolator(Interpolator.EASE_BOTH);

                    RotateTransition rtBack = new RotateTransition(Duration.millis(800), backpackPane);
                    rtBack.setFromAngle(0);
                    rtBack.setToAngle(-45);
                    rtBack.setInterpolator(Interpolator.EASE_BOTH);

                    new ParallelTransition(ttBack, rtBack).play();
                }
                backpackOpen.set(!backpackOpen.get());
            });
        }

        return ui;
    }

}
