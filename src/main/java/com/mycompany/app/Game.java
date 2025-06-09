package com.mycompany.app;

import java.util.*;
import java.util.concurrent.*;

public class Game {
    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    static List<Thread> threads = new ArrayList<Thread>();
    static int day = 0;
    static int hour = 0;

    public static int getHour() {
        return hour;
    }

    List<Pet> adoptedPets = new ArrayList<>();
    static final double timeRate = 200;

    public Game(String saveFile) {
        // Process saving file
        // this.day = 0;
    }

    public Game() {
    }

    public static void init() {
        // Hourly
        scheduler.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                ++hour;
                if (hour == 24) {
                    hour = 0;
                    ++day;
                }
                // System.out.println("[info] time changed to day " + day + " hour " + hour);

                Thread thread = new Thread(() -> {
                    WeatherService ws = new WeatherService("05719e482b5f28e12b521f6e618635c4");
                    try {
                        WeatherType weather = ws.getCurrentWeather("Nanchang");
                        Home.getInstance().updateWeather(weather);
                        // System.out.println("[info] weather updated to " + weather);
                    } catch (InterruptedException e) {
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
                threads.add(thread);
            }
        }, 0, (long) (60 * 1000 / timeRate), TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                var homePets = Home.getInstance().getPets();
                for (Pet pet : homePets) {
                    if (pet.getHunger() < 60) {
                        pet.setHealth(pet.getHealth() - 5);
                    }
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
        }, 0, (long) (24 * 60 * 1000 / timeRate), TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(new TimerTask() {
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
        }, 0, (long) (48 * 60 * 1000 / timeRate), TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // System.out.println("[debug] test timer, should be triggered every 1s");
            }
        }, 0, 1, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // System.out.println("[debug] test timer, should be triggered every 5s");
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public static void terminate() {
        for (var t : threads) {
            t.interrupt();
        }
        scheduler.shutdown();
    }

    public void save(String saveFile) {

    }

    public void load(String saveFile) {

    }
}
