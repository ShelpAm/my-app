package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

public class Bird extends Pet {
    public Bird(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness, 15, calcAdoptionRequirements());
    }

    private static List<AdoptionRequirement> calcAdoptionRequirements() {
        List<AdoptionRequirement> requirements = new ArrayList<>();
        requirements.add(new AdoptionRequirement() {
            @Override
            public boolean check(Player player) {
                return player.getBag().content.containsKey("鸟笼");
            }
        });
        return requirements;
    }

    public int changeMood(WeatherType type) {
        if (type.name() == "CLEAR") { // sunny
            return 10;
        } else if (type.name() == "RAIN") { // rain
            return -10;
        } else { // snow
            return -10;
        }
    }

    public void interact() {
        String[] actions = {
                name + "用头蹭了蹭你的腿",
                name + "发出咕噜咕噜的声音",
                name + "在你面前伸了个懒腰",
                name + "好奇地盯着你看",
                name + "轻轻抓了抓你的衣服"
        };
        System.out.println(actions[(int) (Math.random() * actions.length)]);
        mood = Math.min(100, mood + 5);
    }

    public void update(double deltaTime) {

    }

}

class YingWu extends Bird {
    public YingWu(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }

    @Override
    public String getImagePath() {
        return "/pets/bird-2.png";
    }
}

class BaiLingNiao extends Bird {
    public BaiLingNiao(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }

    @Override
    public String getImagePath() {
        return "/pets/bird-1.png";
    }
}

class ZhenZhuNiao extends Bird {
    public ZhenZhuNiao(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }

    @Override
    public String getImagePath() {
        return "/pets/bird-3.png";
    }
}