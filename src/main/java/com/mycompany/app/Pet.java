package com.mycompany.app;

import java.util.List;

public abstract class Pet {
    protected String name;
    protected int age;
    protected int health;
    protected int hunger;
    protected int mood;
    protected int cleanliness;
    protected int lifeTime;
    protected boolean hasDie;
    private String imagePath;

    protected List<AdoptionRequirement> adoptionRequirements;

    public Pet(String name, int age, int health, int hunger, int mood,
            int cleanliness, int lifeTime, List<AdoptionRequirement> adoptionRequirements) {
        this.name = name;
        this.age = age;
        this.health = health;
        this.hunger = hunger;
        this.mood = mood;
        this.cleanliness = cleanliness;
        this.lifeTime = lifeTime;
        this.adoptionRequirements = adoptionRequirements;
    }

    public abstract int changeMood(WeatherType type);

    public void setDie() {
        this.hasDie = true;
    }

    public boolean hasDie() {
        return this.age >= this.lifeTime;
    }

    public int getHealth() {
        return health;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract void eat(Food food);

    public abstract void interact();

    public abstract void play(Toy toy);

    public abstract void update(double deltaTime);

    public void aging(double deltaTime) {
        age += 0; // TODO
    }

    public abstract void clean();

    public boolean checkAdoptionRequirements(Player player) {
        for (var adoptionRule : adoptionRequirements) {
            if (!adoptionRule.check(player)) {
                return false;
            }
        }
        return true;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHunger() {
        return this.hunger;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(int cleanliness) {
        this.cleanliness = cleanliness;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public List<AdoptionRequirement> getAdoptionRequirements() {
        return adoptionRequirements;
    }

    public void setAdoptionRequirements(List<AdoptionRequirement> adoptionRequirements) {
        this.adoptionRequirements = adoptionRequirements;
    }

    public boolean isHasDie() {
        return hasDie;
    }

    public void setHasDie(boolean hasDie) {
        this.hasDie = hasDie;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
