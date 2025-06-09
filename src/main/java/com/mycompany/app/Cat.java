package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cat extends Pet {
    public Cat(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness, 20, calcAdoptionRequirements());
    }

    private static List<AdoptionRequirement> calcAdoptionRequirements() {
        List<AdoptionRequirement> requirements = new ArrayList<>();
        requirements.add(new AdoptionRequirement() {
            @Override
            public boolean check(Player player) {
                return player.getBag().content.containsKey("猫砂") && player.getBag().content.containsKey("宠物窝");
            }
        });
        return requirements;
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

    public int changeMood(WeatherType type) {
        if (type.name() == "CLEAR") { // sunny
            return -10;
        } else if (type.name() == "RAIN") { // rain
            return 10;
        } else { // snow
            return -10;
        }
    }

    public void update(double deltaTime) {

    }

}

class LiHuaMao extends Cat {
    public LiHuaMao(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }

    @Override
    public String getImagePath() {
        return "/pets/cat-2.png";
    }
}

class YingDuan extends Cat {
    public YingDuan(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }

    @Override
    public String getImagePath() {
        return "/pets/cat-1.png";
    }
}

class BuOuMao extends Cat {
    public BuOuMao(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }

    @Override
    public String getImagePath() {
        return "/pets/cat-5.png";
    }
}

class XianLuoMao extends Cat {
    public XianLuoMao(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }

    @Override
    public String getImagePath() {
        return "/pets/cat-3.png";
    }
}

class MianYinMao extends Cat {
    public MianYinMao(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }

    @Override
    public String getImagePath() {
        return "/pets/cat-6.png";
    }
}
