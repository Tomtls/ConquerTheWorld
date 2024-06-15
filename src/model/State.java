package model;

import java.awt.Color;

public class State {
    private int id;
    private Color color;
    private int units;

    public State(int id, Color color, int units) {
        this.id = id;
        this.color = color;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
