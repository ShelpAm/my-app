package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Bird extends Pet {
    public Bird(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness, 15, calcAdoptionRequirements());
    }
    private static List<AdoptionRequirement> calcAdoptionRequirements() {
        List<AdoptionRequirement> requirements =  new ArrayList<>();
        requirements.add(new AdoptionRequirement() {
            @Override
            public boolean check(Player player) {
                return player.getBag().content.containsKey("鸟笼");
            }
        });
        return requirements;
    }
    public void eat(Food food) {

    }

    public int changeMood() {
        Random rd = new Random(5);
        int t = rd.nextInt();
        if (t == 0 || t == 1) {
            return 10;
        }
        else return -10;
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

class YingWu extends Bird {
    public YingWu(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }
}

class BaiLingNiao extends Bird {
    public BaiLingNiao(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }
}

class ZhenZhuNiao extends Bird {
    public ZhenZhuNiao(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }
}