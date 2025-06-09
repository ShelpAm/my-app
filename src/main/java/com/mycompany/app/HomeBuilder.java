package com.mycompany.app;

import javafx.util.Duration;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class HomeBuilder {

    private final SceneManager sceneManager;
    private Button birdButton;

    public HomeBuilder(SceneManager sm) {
        this.sceneManager = sm;
    }

    public Pane makeHome() {
        Pane home = new Pane();
        home.getStylesheets().add(getClass().getResource("/home.css").toExternalForm());
        home.setId("home");
        home.setPrefSize(sceneManager.getScene().getWidth(), sceneManager.getScene().getHeight());

        final Point2D[] mousePos = { new Point2D(0, 0) };
        home.setOnMouseMoved(e -> {
            mousePos[0] = new Point2D(e.getSceneX(), e.getSceneY());
        });

        ClippedImageButton door = new ClippedImageButton("/door.png");
        door.setOnAction(e -> sceneManager.enter(SceneType.MainStreet));
        home.getChildren().add(door);

        { // Pets
            ArrayList<Button> blanket = new ArrayList<>();
            {
                Button btn = new ClippedImageButton("/pets/cat-1.png", null);
                btn.setScaleX(0.5);
                btn.setScaleY(0.5);
                btn.setLayoutX(200);
                btn.setLayoutY(350);
                blanket.add(btn);

                btn.setOnAction(e -> {
                    double x = mousePos[0].getX();
                    double y = mousePos[0].getY();

                    PopupButtonHelper.showPopupButtons(
                            home,
                            x, y,
                            new String[] { "/1.png", "/2.png", "/1.png" },
                            () -> System.out.println("执行技能 1"),
                            () -> System.out.println("执行技能 2"),
                            () -> System.out.println("执行技能 3"));
                });

            }
            {
                Button btn = new ClippedImageButton("/pets/dog-2.png", null);
                btn.setScaleX(0.5);
                btn.setScaleY(0.5);
                btn.setLayoutX(500);
                btn.setLayoutY(300);
                blanket.add(btn);
            }
            {
                Button btn = new ClippedImageButton("/pets/bird-2.png", null);
                btn.setScaleX(0.5);
                btn.setScaleY(0.5);
                btn.setLayoutX(300);
                btn.setLayoutY(450);
                blanket.add(btn);
            }
            {
                Button btn = new ClippedImageButton("/pets/bird-2.png", null);
                btn.setScaleX(0.5);
                btn.setScaleY(0.5);
                btn.setLayoutX(550);
                btn.setLayoutY(400);
                blanket.add(btn);
            }
            for (var slot : blanket) {
                if (slot != null) {
                    home.getChildren().add(slot);
                }
            }

            // 当买鸟时，才调用updateBird。

        }

        Timeline refresher = new Timeline(
                new KeyFrame(Duration.minutes(5), e -> {
                    WeatherService ws = new WeatherService("05719e482b5f28e12b521f6e618635c4");
                    try {
                        updateWeather(ws.getCurrentWeather("Nanchang"));
                    } catch (Exception ex) {
                    }
                }));
        refresher.setCycleCount(Timeline.INDEFINITE);
        refresher.play();

        return home;
    }

    private void updateWeather(WeatherType type) {
        for (Pet pet : Home.getInstance().getPets()) {
            pet.setMood(pet.getMood() - pet.changeMood(type));
        }

    }

    private void updateBird(Bird bird, Pane home) {
        if (bird == null) {
            return;
        }

        if (birdButton == null) {
            return;
        }

        assert home == (Pane) birdButton.getParent();
        home.getChildren().remove(birdButton);

        birdButton = new ClippedImageButton(bird.getImagePath(), null);
        birdButton.setScaleX(0.5);
        birdButton.setScaleY(0.5);
        birdButton.setLayoutX(-220);
        birdButton.setLayoutY(480);
        home.getChildren().add(birdButton);
    }

}
