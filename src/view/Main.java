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
import network.GameServer;

public class Main {
    private static JFrame frame;
    private PreGamePanel preGamePanel;
    private boolean resizing = false;
    private Player player;
    private Game game;
    private MapPanel mapPanel;
    private GameServer server;
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

    public void startGame(String name, Color color){
        player = new Player(name, color);
        game = new Game(player);
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

    public void startMultiplayer(String name, Color color){
        frame.remove(preGamePanel);
        frame.getContentPane().removeAll(); //entfernt alles
        multi = new MultiplayerSetupPanel(this, name, color);
        frame.add(multi);
        frame.revalidate();
        frame.repaint();
    }
    public void startAsServer(String name, Color color){
        frame.remove(multi);

        game = new Game(new Player(name, color));
        serverWaitingPanel = new ServerWaitingPanel(this, name, color);
        //controller = new GameController(game, mapPanel);
        //mapPanel ändern?
        
        if (!serverStarted){
            startStream(game);
            serverStarted = true;
        }

        frame.add(serverWaitingPanel);
        frame.revalidate();
        frame.repaint();
        // warten bis client gestartet wird
    }

    public void startAsClient(String name, Color color){
        frame.remove(multi);
        clientConnectingPanel = new ClientConnectingPanel(this, name, color);
        frame.add(clientConnectingPanel);
        frame.revalidate();
        frame.repaint();
    }

    public static JFrame getFrame() {
        return frame;
    }
      
        //Stream
    public void startStream(Game game) {
        new Thread(() -> {
            server = new GameServer(game);
        }).start();
    }
}