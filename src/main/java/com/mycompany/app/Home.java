package com.mycompany.app;

import java.util.List;
import java.util.ArrayList;

public class Home {
    private static Home instance = new Home();

    private List<Pet> pets = new ArrayList<>();

    public void add(Pet pet) {
        pets.add(pet);
    }

    public List<Pet> getPets() {
        return pets;
    }

    public static Home getInstance() {
        return instance;
    }
}
