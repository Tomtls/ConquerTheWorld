package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controller.GameController;
import model.Game;
import model.Player;

public class Main {
    private static JFrame frame;
    private PreGamePanel preGamePanel;
    private boolean resizing = false;
    private Player player1;
    private Game game;
    public MapPanel mapPanel;

    public Main(){
        frame = new JFrame("Conquer the world!");
        preGamePanel = new PreGamePanel(Main.this);;
        setWindow();
     }

    public void setWindow(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);
        frame.setLayout(null);

        setStartView();
    }

    private void setStartView(){
        frame.add(preGamePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    public void startGame(String name, Color color){
        player1 = new Player(name, color);
        game = new Game(player1, player1);
        mapPanel = new MapPanel(game); 
        GameController gameController = new GameController(game, mapPanel);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (!resizing) {
                    resizing = true;
                    SwingUtilities.invokeLater(() -> {
                        mapPanel.adjustPanel(frame);
                        System.out.println("adjuse");  
                        resizing = false;
                    });
                }        
            }
        });

        frame.remove(preGamePanel);
        frame.add(mapPanel);
        frame.revalidate();
        frame.repaint();
    }

    public static JFrame getFrame() {
        return frame;
    }
      
}