package model;
import java.awt.Color;

public class Unit {
    private double x,y;
    private Color color;

    public Unit(double x, double y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public Color getColor() {
        return color;
    }
    public static void calculateUnits (int startID, int zielID, Color[] color, int[] unit){
        
        if (color[startID] == color[zielID]){
            unit[zielID] += unit[startID];
            unit[startID] = 0;
        }
        
        else{
            int unitDifference  = unit[zielID] - unit[startID];
            unit[startID] = 0;
            
            if (unitDifference  < 0){
                unitDifference  *= -1;
                unit[zielID] = unitDifference ;
                color[zielID] = color[startID];
            }
            else unit[zielID] = unitDifference ;
        }
    }
}