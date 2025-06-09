package com.mycompany.app;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Dog extends Pet {
    public Dog(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness, 20, calcAdoptionRequirements());
    }

    private static List<AdoptionRequirement> calcAdoptionRequirements() {
        List<AdoptionRequirement> requirements =  new ArrayList<>();
        requirements.add(new AdoptionRequirement() {
            @Override
            public boolean check(Player player) {
                return player.getBag().content.containsKey("狗绳") && player.getBag().content.containsKey("宠物窝");
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
            return 0;
        }
    }

    public void interact() {
        String[] actions = {
                name + "摇着尾巴向你跑来",
                name + "汪汪叫了两声",
                name + "舔了舔你的手",
                name + "把玩具叼到了你面前",
                name + "开心地在地上打滚"
        };
        System.out.println(actions[(int) (Math.random() * actions.length)]);
        mood = Math.min(100, mood + 5);
    }

    public void play(Toy toy) {

    }

    public void update(double deltaTime) {

    }

    public void clean() {
        System.out.println(name + "很抗拒，但最终还是洗完了");
        cleanliness = 100;
        System.out.println(name + "现在很干净");
    }
}

class JinMaoDog extends Dog {
    public JinMaoDog(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }
}

class HaShiQi extends Dog {
    public HaShiQi(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }
}

class BianMu extends Dog {
    public BianMu(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }

}

class ChaiQuan extends Dog {
    public ChaiQuan(String name, int age, int health, int hunger, int mood, int cleanliness) {
        super(name, age, health, hunger, mood, cleanliness);
    }
}
