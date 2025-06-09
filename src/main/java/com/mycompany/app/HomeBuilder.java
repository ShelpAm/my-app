package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class HomeBuilder {

    private final SceneManager sceneManager;
    private Button birdButton;
    private Button fishButton;

    public Button getFishButton() {
        return fishButton;
    }

    private Pane weatherContainer;
    private Pane birdContainer;

    Pane home;

    public Pane getHome() {
        return home;
    }

    public Pane getBirdContainer() {
        return birdContainer;
    }

    public void setHome(Pane home) {
        this.home = home;
    }

    public Button getBirdButton() {
        return birdButton;
    }

    public Pane getWeatherContainer() {
        return weatherContainer;
    }

    public void setWeatherContainer(Pane weatherContainer) {
        this.weatherContainer = weatherContainer;
    }

    public HomeBuilder(SceneManager sm) {
        this.sceneManager = sm;
        Home.getInstance().setHomeBuilder(this);
    }

    final Point2D[] mousePos = { new Point2D(0, 0) };

    public Pane makeHome() {
        home = new Pane();
        home.getStylesheets().add(getClass().getResource("/home.css").toExternalForm());
        home.setId("home");
        home.setPrefSize(sceneManager.getScene().getWidth(), sceneManager.getScene().getHeight());

        home.setOnMouseMoved(e -> {
            mousePos[0] = new Point2D(e.getSceneX(), e.getSceneY());
        });

        { // Backgrounds
            weatherContainer = new Pane();
            weatherContainer.setPrefSize(sceneManager.getScene().getWidth(),
                    sceneManager.getScene().getHeight());
            home.getChildren().add(weatherContainer);
        }

        {
            ClippedImageButton door = new ClippedImageButton("/door.png");
            door.setOnAction(e -> sceneManager.enter(SceneType.MainStreet));
            home.getChildren().add(door);
        }

        { // Pets
            ArrayList<Button> blanket = new ArrayList<>();
            Point2D[] layouts = new Point2D[] {
                    new Point2D(200, 350),
                    new Point2D(500, 300),
                    new Point2D(300, 450),
                    new Point2D(550, 400)
            };
            int i = 0;
            for (var pet : Home.getInstance().getPets()) {
                Button btn = new ClippedImageButton(pet.getImagePath(), null);
                btn.setScaleX(0.5);
                btn.setScaleY(0.5);
                btn.setLayoutX(layouts[i].getX());
                btn.setLayoutY(layouts[i].getY());
                blanket.add(btn);

                btn.setOnAction(e -> {
                    double x = mousePos[0].getX();
                    double y = mousePos[0].getY();

                    PopupButtonHelper.showPopupButtons(
                            home,
                            x, y,
                            new String[] { "/zhuazi.png", "/zhuazi.png", "/zhuazi.png" },
                            () -> pet.clean(),
                            () -> pet.play((Toy) Toy.getDefaultToys().getFirst()),
                            () -> pet.eat((Food) Food.getDefaultFoods().getFirst()));
                });
                home.getChildren().add(btn);
                ++i;
            }

            // 当买鸟时，才调用updateBird。

        }

        {
            birdContainer = new Pane();
            birdContainer.setPrefSize(sceneManager.getScene().getWidth(),
                    sceneManager.getScene().getHeight());
            birdContainer.setPickOnBounds(false);
            home.getChildren().add(birdContainer);
        }

        return home;
    }

    List<Button> petButtons = new ArrayList<>();

}
