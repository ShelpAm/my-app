package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

public abstract class Pet extends Item {
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

    public static List<Item> getDefaultPets() {
        List<Item> pets = new ArrayList<>();

        // 狗类
        pets.add(new JinMaoDog("金毛", 1, 100, 50, 50, 80));
        pets.add(new HaShiQi("哈士奇", 1, 100, 50, 50, 80));
        pets.add(new BianMu("边牧", 1, 100, 50, 50, 80));
        pets.add(new ChaiQuan("柴犬", 1, 100, 50, 50, 80));

        // 猫类
        pets.add(new LiHuaMao("狸花猫", 1, 100, 50, 50, 80));
        pets.add(new YingDuan("英短", 1, 100, 50, 50, 80));
        pets.add(new BuOuMao("布偶猫", 1, 100, 50, 50, 80));
        pets.add(new XianLuoMao("暹罗猫", 1, 100, 50, 50, 80));
        pets.add(new MianYinMao("缅因猫", 1, 100, 50, 50, 80));

        // 鸟类
        pets.add(new BaiLingNiao("白领鸟", 1, 100, 50, 50, 80));
        pets.add(new YingWu("鹦鹉", 1, 100, 50, 50, 80));
        pets.add(new ZhenZhuNiao("珍珠鸟", 1, 100, 50, 50, 80));

        return pets;
    }

    public Pet(String name, int age, int health, int hunger, int mood,
            int cleanliness, int lifeTime, List<AdoptionRequirement> adoptionRequirements) {
        super(name, 100, null, "我是宠物！！哈哈", 0);

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

    public void eat(Food food) {
        MessageLabel l = new MessageLabel(Home.getInstance().getHomeBuilder().home);
        l.showMessage("真好吃！", 3);
    }

    public abstract void interact();

    public void play(Toy toy) {
        MessageLabel l = new MessageLabel(Home.getInstance().getHomeBuilder().home);
        l.showMessage("下次继续", 3);
    }

    public abstract void update(double deltaTime);

    public void aging(double deltaTime) {
        age += 0; // TODO
    }

    public void clean() {
        MessageLabel l = new MessageLabel(Home.getInstance().getHomeBuilder().home);
        l.showMessage("你还没有清洁用品！请先购买一个！", 3);
    }

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
        if (imagePath == null) {
            System.out.println("[warning] You must forget to set image path for this pet (" + name + ")");
        }
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
