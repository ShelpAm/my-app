package com.mycompany.app;

import java.util.Random;

public abstract class RandomExecution {

    private Random random = new Random();
    private double probability;

    public RandomExecution(double probability) {
        this.probability = probability;
    }

    public void trigger() {
        if (random.nextDouble() < probability) {
            run();
        }

    }

    public abstract void run();
}
