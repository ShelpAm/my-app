package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

public class Home {
    private static Home instance = new Home();
    private HomeBuilder homeBuilder;

    public HomeBuilder getHomeBuilder() {
        return homeBuilder;
    }

    public void setHomeBuilder(HomeBuilder homeBuilder) {
        this.homeBuilder = homeBuilder;
    }

    private List<Pet> pets = new ArrayList<>();

    public static Home getInstance() {
        return instance;
    }

    public void add(Pet pet) {
        pets.add(pet);
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void updateWeather(WeatherType type) {
        String weatherImagePath = "/scenes/";
        // Day
        if (6 <= Game.getHour() && Game.getHour() < 18) {
            weatherImagePath += "day";
        } else { // Night
            weatherImagePath += "night";
        }
        weatherImagePath += "-";
        if (type == WeatherType.RAIN || type == WeatherType.CLOUDS) {
            weatherImagePath += "rain";
        } else if (type == WeatherType.SNOW) {
            weatherImagePath += "snow";
        } else if (type == WeatherType.CLEAR) {
            weatherImagePath += "clear";
        } else {
            System.out.println("[warning] current wather is " + type
                    + ", cannot match with the three. Using default day-clear.png");
            weatherImagePath = "/scenes/day-clear";
        }
        weatherImagePath += ".png";
        System.out.println("[info] weatherImagePath: " + weatherImagePath);

        // Special for this kind of condition.
        if (weatherImagePath.equals("/scenes/day-clear.png")) {
            homeBuilder.getWeatherContainer().setBackground(Background.EMPTY);
        } else {
            Image image = new Image(getClass().getResource(weatherImagePath).toExternalForm());
            BackgroundImage bgImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
            homeBuilder.getWeatherContainer().setBackground(new Background(bgImage));
        }

        for (Pet pet : Home.getInstance().getPets()) {
            pet.setMood(pet.getMood() - pet.changeMood(type));
        }
    }

    private void updateBird(Bird bird) {
        var birdButton = homeBuilder.getBirdButton();
        var home = homeBuilder.getHome();

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

    public void updatePets() {
        var petButtons = homeBuilder.petButtons;
        var mousePos = homeBuilder.mousePos;
        var home = homeBuilder.home;

        // 先清空旧的宠物按钮
        if (petButtons != null) {
            home.getChildren().removeAll(petButtons);
        }
        petButtons.clear();

        Point2D[] layouts = new Point2D[] {
                new Point2D(200, 350),
                new Point2D(500, 300),
                new Point2D(300, 450),
                new Point2D(550, 400)
        };

        int i = 0;
        for (var pet : Home.getInstance().getPets()) {
            if (i >= layouts.length)
                break; // 防止越界
            Button btn = new ClippedImageButton(pet.getImagePath(), null);
            btn.setScaleX(0.5);
            btn.setScaleY(0.5);
            btn.setLayoutX(layouts[i].getX());
            btn.setLayoutY(layouts[i].getY());

            btn.setOnAction(e -> {
                double x = mousePos[0].getX();
                double y = mousePos[0].getY();

                PopupButtonHelper.showPopupButtons(
                        home,
                        x, y,
                        new String[] { "/1.png", "/2.png", "/1.png" },
                        () -> System.out.println("执行技能 1 for " + pet.getName()),
                        () -> System.out.println("执行技能 2 for " + pet.getName()),
                        () -> System.out.println("执行技能 3 for " + pet.getName()));
            });

            petButtons.add(btn);
            home.getChildren().add(btn);
            ++i;
        }
    }

}
