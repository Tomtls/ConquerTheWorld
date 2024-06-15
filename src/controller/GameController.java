package controller;



import model.Game;
import model.State;
import view.IDButton;
import view.MapPanel;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

public class GameController extends MouseAdapter {
    private Game game;
    private MapPanel mapPanel;

    public GameController(Game game, MapPanel mapPanel) {
        this.game = game;
        this.mapPanel = mapPanel;
        Timer timer = new Timer(1000, updateUnits());
        timer.start();
    }

    private ActionListener updateUnits() {
        return e -> {
            game.updateStates();
            for (int i = 0; i < game.getStateCount(); i++) {
                State state = game.getState(i);
                mapPanel.updateButton(i,state);                
            }
        };
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof IDButton) {
            IDButton button = (IDButton) e.getSource();
            // Logik für das Drücken der Maustaste hier hinzufügen
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof IDButton) {
            IDButton button = (IDButton) e.getSource();
            // Logik für das Loslassen der Maustaste hier hinzufügen
        }
    }
}
