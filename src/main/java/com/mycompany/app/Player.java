package com.mycompany.app;

import java.util.ArrayList;

/**
 * Player
 */
public class Player {

    private String name;
    private Bag bag = new Bag();
    private ArrayList<Pet> myPets = new ArrayList<>();
    private int money;

    private static Player instance = new Player("You");

    public static Player getInstance() {
        return instance;
    }

    private Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Pet getPetByName(String name) {
        for (Pet pet : myPets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                return pet;
            }
        }
        return null; // If not found
    }

    public boolean canAfford(Item item) {
        return money >= item.price();
    }

    public void addMoney(int delta) {
        money += delta;
    }

    // Returns whether the adoption is sucessful
    public boolean adopt(PetAdoptionCenter petAdoptionCenter, Pet pet) {
        try {
            petAdoptionCenter.adoptFor(pet, this);
            myPets.add(pet);
            return true;
        } catch (PetNotFound e) { // This should never happen.
        }
        return false;
    }

    public int getMoney() {
        return money;
    }

    public Bag getBag() {
        return bag;
    }

}
