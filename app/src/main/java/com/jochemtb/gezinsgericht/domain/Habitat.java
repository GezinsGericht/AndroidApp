package com.jochemtb.gezinsgericht.domain;

import java.util.ArrayList;

public class Habitat {

    private ArrayList<String> habitat;

    public Habitat(ArrayList<String> habitats) {
        this.habitat = habitats;
    }

    public ArrayList<String> getHabitat() {
        return habitat;
    }

    public void setHabitat(ArrayList<String> habitat) {
        this.habitat = habitat;
    }

    public String getHabitatById(int id) {
        return habitat.get(id);
    }
}
