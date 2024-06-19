package model;

import java.io.Serializable;

public class State implements Serializable{
    private int id;
    private Player owner;
    private int units;

    public State(int id, Player owner, int units) {
        this.id = id;
        this.owner = owner;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public Player getOwner() {
        return owner;
    }

    public void changeOwner(Player newOwner, int units) {
        this.owner = newOwner;
        this.units = units; 
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public void incrementUnits() {
        this.units++;
    }
}
