package com.mycompany.app;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

public class Game {
    Timer timer = new Timer();
    int day = 0;
    List<Pet> adoptedPets = new ArrayList<>();
    final double timeRate = 200;

    public Game(String saveFile) {
        // Process saving file
        // this.day = 0;
    }

    public Game() {
    }

    public void init() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                ++day;

                var homePets = Home.getInstance().getPets();
                for (Pet pet : homePets) {
                    if (pet.getHunger() < 60) {
                        pet.setHealth(pet.getHealth() - 5);
                    }
                }

                for (Pet pet : homePets) {
                    pet.setMood(pet.getMood() - pet.changeMood());
                }

                for (Pet pet : homePets) {
                    if (pet.getHealth() < 60) {
                        double x = pet.getHealth();
                        double p = x * x / 48 - 37.0 / 12 * x + 100;
                        RandomExecution re = new RandomExecution(p) {
                            @Override
                            public void run() {
                                pet.setHealth(0);
                            }
                        };
                        re.run();

                    }
                }
            }
        }, 0, (long) (24 * 60 * 1000 / timeRate));

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                var homePets = Home.getInstance().getPets();
                for (Pet pet : homePets) {
                    pet.age += 1;
                    if (pet.age >= pet.lifeTime) {
                        pet.setDie();
                    }
                    if (pet.hasDie()) {
                        System.out.println(pet.getName() + "死了 亩,享年" + pet.getAge() + "岁");
                    }
                }
            }
        }, 0, (long) (48 * 60 * 1000 / timeRate));

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("Test timer, should be triggered every 5s");
            }
        }, 0, (long) (5 * 1000));
    }

    public void save(String saveFile) {

    }

    public void load(String saveFile) {

    }
}
