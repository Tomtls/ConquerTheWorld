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
}