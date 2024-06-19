package model;

import java.awt.Color;
import java.io.Serializable;

public class Player implements Serializable{
    private String name;
    private Color color;

    public Player(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }
    
    public Color getColor(){
        return color;
    }
}
