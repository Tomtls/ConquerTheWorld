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
    private IDButton firstClickedButton;

    public GameController(Game game, MapPanel mapPanel) {
        this.game = game;
        this.mapPanel = mapPanel;
        Timer timer = new Timer(1000, updateUnits());
        timer.start();
    }

    private ActionListener updateUnits() {
        return e -> {
            game.updateUnits();
            for (int i = 0; i < game.getStateCount(); i++) {
                State state = game.getState(i);
                mapPanel.updateButton(i,state);                
            }
        };
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof IDButton) {
            IDButton button = (IDButton) e.getSource();
            // Speichern des zuerst geklickten Buttons
            firstClickedButton = button;
            System.out.println("mouse pressed button with id: " + button.getId());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof IDButton) {
            IDButton button = (IDButton) e.getSource();
            if (firstClickedButton != null) { 
                if (buttonBelongsToCurrentPlayer(button)) {
                    // Logik für das Loslassen der Maustaste hier hinzufügen
                    System.out.println("mouse released button with id: " + button.getId());
                }
                firstClickedButton = null;
            }
        }
    }

    private boolean buttonBelongsToCurrentPlayer(IDButton button) {
        return game.getState(button.getId()).getOwner().equals(game.getCurrentPlayer());
    }
}
