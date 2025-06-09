package com.mycompany.app;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.util.ArrayList;

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

}
