package com.mycompany.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Logic error
class PetNotFound extends Error {
}

/**
 * PetAdoptionCenter
 */
public class PetAdoptionCenter {

    private static PetAdoptionCenter instance = new PetAdoptionCenter();

    private ObservableList<Pet> pets = FXCollections.observableArrayList();

    public ObservableList<Pet> getPets() {
        return pets;
    }

    public void add(Pet pet) {
        pets.add(pet);
    }

    boolean adoptFor(Pet pet, Player player) {
        if (!pets.contains(pet)) {
            throw new PetNotFound();
        }
        if (!pet.checkAdoptionRequirements(player)) {
            return false;
        }
        pets.remove(pet);
        return true;
    }

    public static PetAdoptionCenter getInstance() {
        return instance;
    }
}
