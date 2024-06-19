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
import network.GameClient;
import network.GameServer;

public class Main {
    private static JFrame frame;
    private PreGamePanel preGamePanel;
    private boolean resizing = false;
    private Player player;
    private Game game;
    private MapPanel mapPanel;
    private GameServer server;
    private GameClient client;
    private boolean serverStarted = false;
    private ServerWaitingPanel serverWaitingPanel;
    private GameController controller;
    private MultiplayerSetupPanel multi;
    private ClientConnectingPanel clientConnectingPanel;

    public Main(){
        frame = new JFrame("Conquer the world!");
        preGamePanel = new PreGamePanel(Main.this);

     }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setWindow());
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

    public void startSingleGame(String name, Color color){
        player = new Player(name, color);
        game = new Game();
        game.addPlayer(player);
        mapPanel = new MapPanel(game); 
        GameController gameController = new GameController(game, mapPanel); 
        mapPanel.setGameController(gameController);
        gameController.startTimer();       

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (!resizing) {
                    resizing = true;
                    SwingUtilities.invokeLater(() -> {
                        mapPanel.adjustPanel(frame);  
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

    public void startMultiplayerSetup(String name, Color color){
        frame.remove(preGamePanel);
        frame.getContentPane().removeAll(); //entfernt alles
        multi = new MultiplayerSetupPanel(this, name, color);
        frame.add(multi);
        frame.revalidate();
        frame.repaint();
    }
    public void startAsServer(String name, Color color){
        player = new Player(name, color);
        game = new Game();
        game.addPlayer(player);
        mapPanel = new MapPanel(game);
        GameController gameController = new GameController(game, mapPanel);
        mapPanel.setGameController(gameController);

        frame.remove(multi);

        serverWaitingPanel = new ServerWaitingPanel(this, name, color);
        
        if (!serverStarted){
            server = new GameServer(game);
            new Thread(server).start();
            serverStarted = true;
        }

        frame.add(mapPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void startAsClient(String name, Color color){
        frame.remove(multi);
        clientConnectingPanel = new ClientConnectingPanel(this, name, color);
        frame.add(clientConnectingPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    public void startClient(String name, Color color, String ip){
        player = new Player(name, color);
        client = new GameClient(player);
        client.connect(ip);
        frame.remove(clientConnectingPanel);

        //test
        Game game = new Game();

        new Thread(() -> {
            while (client.getGame() == null) {
                try {
                    Thread.sleep(100); // Warten Sie kurz, bevor Sie erneut prüfen
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    
            SwingUtilities.invokeLater(() -> {
                // Initialisierung von mapPanel, falls es noch nicht initialisiert wurde
                if (mapPanel == null) {
                    mapPanel = new MapPanel(client.getGame()); // Stellen Sie sicher, dass client.getGame() den richtigen Spielzustand zurückgibt
                    System.out.println("MapPanel initialisiert und hinzugefügt");
                    frame.add(mapPanel);
                    mapPanel.adjustPanel(frame); // Stellen Sie sicher, dass die Panelgröße angepasst wird
                } else {
                    // mapPanel ist bereits initialisiert, setzen Sie gegebenenfalls das Spiel neu
                    mapPanel.setGame(client.getGame()); // Setzen Sie das Spiel erneut, falls notwendig
                    System.out.println("MapPanel gesetzt und aktualisiert");
                }
    
                GameController gameController = new GameController(client.getGame(), mapPanel);
                mapPanel.setGameController(gameController);
                gameController.startTimer();
                frame.add(mapPanel);
                frame.revalidate();
                frame.repaint();
    
                // Debug-Ausgaben zur Überprüfung
                System.out.println("MapPanel hinzugefügt: " + (mapPanel.getParent() != null));
                System.out.println("Frame Größe: " + frame.getSize());
            });
        }).start();/* 
    // Initialisierung von mapPanel, falls es noch nicht initialisiert wurde
    if (mapPanel == null) {
        mapPanel = new MapPanel(client.getGame()); // Stellen Sie sicher, dass client.getGame() den richtigen Spielzustand zurückgibt
        frame.add(mapPanel);
        mapPanel.adjustPanel(frame); // Stellen Sie sicher, dass die Panelgröße angepasst wird
        frame.revalidate();
        frame.repaint();
    } else {
        // mapPanel ist bereits initialisiert, setzen Sie gegebenenfalls das Spiel neu
        mapPanel.setGame(client.getGame()); // Setzen Sie das Spiel erneut, falls notwendig
    }

    GameController gameController = new GameController(client.getGame(), mapPanel);
    mapPanel.setGameController(gameController);

    frame.remove(clientConnectingPanel);
    frame.add(mapPanel);
    frame.revalidate();
    frame.repaint();*/
    }
    public static JFrame getFrame() {
        return frame;
    }


}