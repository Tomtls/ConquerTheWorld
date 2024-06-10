import java.awt.Color;

public class UnitsCalculator {
    
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