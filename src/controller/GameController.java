package controller;

import model.Game;
import model.IDButton;
import model.State;
import network.GameClient;
import view.Main;
import view.MapPanel;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GameController extends MouseAdapter {
    private Main main;
    private Game game;
    private MapPanel mapPanel;
    private IDButton pressedButton;
    private GameClient client;
    private boolean multiplayer;
    public static Timer timer;

    public GameController(Main main, Game game, MapPanel mapPanel, GameClient client) {
        this.main = main;
        this.game = game;
        this.mapPanel = mapPanel;
        this.client = client;       
        this.multiplayer = true; 
    }

    public GameController(Game game, MapPanel mapPanel) {
        this.game = game;
        this.mapPanel = mapPanel;
        this.multiplayer = false;
        timer = new Timer(1000, updateUnits());
        timer.start();
    }
    
    private ActionListener updateUnits() {
        return e -> {
            game.updateUnits();
            for (int i = 0; i < game.getStateCount(); i++) {
                State state = game.getState(i);
                mapPanel.updateButton(i,state);
            }
            if (game.isOver()) {
                stopTimer();
                SwingUtilities.invokeLater(() -> mapPanel.showWinnerPanel(game.getWinner()));
            }
        };
    }

    public Game getGame() { return game; }

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
                    int fromID = pressedButton.getId();
                    int toID = targetButton.getId();
                    game.moveUnits(fromID, toID);
                    if (multiplayer){
                        sendMoveToServer(fromID, toID);
                    }
                    else mapPanel.adjustButtons();
                }                
            }
            pressedButton = null;
        }
    }

    private void sendMoveToServer(int fromID, int toID) {
        try {
            client.sendMove(fromID, toID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void stopTimer(){
        timer.stop();
    }

    public void surrender(){
        game.setIsOver(true);
    }

        // Methoden zum Speichern und Laden des Spielstands
    public void saveGame(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this.game);
            timer.stop();
            main.setStartView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            this.game = (Game) ois.readObject();
            // Aktualisieren Sie die MapPanel mit den geladenen Spieldaten
            for (int i = 0; i < game.getStateCount(); i++) {
                State state = game.getState(i);
                mapPanel.updateButton(i, state);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
