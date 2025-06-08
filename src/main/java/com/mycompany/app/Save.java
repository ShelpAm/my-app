package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

public class Save {

    private String name;

    public static List<Save> getAllSaves() {
        List<Save> saves = new ArrayList<>();
        saves.add(new Save());
        saves.add(new Save());
        saves.add(new Save());
        saves.add(new Save());
        saves.add(new Save());
        saves.add(new Save());
        saves.add(new Save());
        return saves;
    }

    private Save() {
        this.name = "default save name";
    }

    public Save(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}
