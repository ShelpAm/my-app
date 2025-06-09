package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class LongMao extends Pet {
    public LongMao(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness, 20, calcAdoptionRequirements());
    }
    private static List<AdoptionRequirement> calcAdoptionRequirements() {
        List<AdoptionRequirement> requirements =  new ArrayList<>();
        requirements.add(new AdoptionRequirement() {
            @Override
            public boolean check(Player player) {
                return player.getBag().content.containsKey("宠物窝");
            }
        });
        return requirements;
    }
    public void eat(Food food) {

    }

    public int changeMood(WeatherType type) {
        if (type.name() == "CLEAR") { // sunny
            return 10;
        }
        else if (type.name() == "RAIN") { // rain
            return -10;
        }
        else { // snow 
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

    public void play(Toy toy) {

    }

    public void update(double deltaTime) {

    }

    public void clean() {
        System.out.println(name + "很享受的洗完了");
        cleanliness = 100;
        System.out.println(name + "现在很干净");
    }
}
