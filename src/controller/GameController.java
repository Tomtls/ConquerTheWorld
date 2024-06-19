package controller;

import model.Game;
import model.IDButton;
import model.State;
import view.MapPanel;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GameController extends MouseAdapter {
    private Game game;
    private MapPanel mapPanel;
    private IDButton pressedButton;
    public static Timer timer;

    public GameController(Game game, MapPanel mapPanel) {
        this.game = game;
        this.mapPanel = mapPanel;
        timer = new Timer(1000, updateUnits());
        
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

    public void startTimer(){
        timer.start();
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getSource() instanceof IDButton) {
            pressedButton = (IDButton) e.getSource();
            pressedButton.getModel().setArmed(true);
            pressedButton.getModel().setPressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && pressedButton != null) {
            pressedButton.getModel().setArmed(false);
            pressedButton.getModel().setPressed(false);

            Point pointOnScreen = e.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(pointOnScreen, pressedButton.getParent());
            Component comp = SwingUtilities.getDeepestComponentAt(pressedButton.getParent(), pointOnScreen.x, pointOnScreen.y);
            
            if (comp instanceof IDButton && comp != pressedButton) {
                IDButton targetButton = (IDButton) comp;
                if (targetButton != pressedButton && targetButton != null && timer.isRunning()) {
                    game.moveUnits(pressedButton.getId(), targetButton.getId());
                }                
            }
            pressedButton = null;
        }
    }

}
