package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private MultiplayerSetupPanel multi;
    private ServerWaitingPanel serverWaitingPanel;
    private ClientConnectingPanel clientConnectingPanel;
    private MapPanel mapPanel;
    private boolean resizing = false;
    private Player player;
    private Game game;
    private GameServer server;
    private GameClient client;
    private static ExecutorService executor = Executors.newCachedThreadPool();
    private final String ip = "127.0.0.1";


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

    public void setStartView(){
        frame.getContentPane().removeAll();
        frame.add(preGamePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    public void startSingleGame(String name, Color color){
        player = new Player(name, color);
        game = new Game(player);
        mapPanel = new MapPanel(game); 
        GameController gameController = new GameController(game, mapPanel); 
        mapPanel.setGameController(gameController);      

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
        frame.getContentPane().removeAll(); //entfernt alles
        multi = new MultiplayerSetupPanel(this, name, color);
        frame.add(multi);
        frame.revalidate();
        frame.repaint();
    }
    public void startAsServer(String name, Color color) {
        server = new GameServer();
        executor.execute(server::start);

        serverWaitingPanel = new ServerWaitingPanel(this, name, color);
        frame.add(serverWaitingPanel);
        frame.revalidate();
        frame.repaint();

        startClient(name, color, ip);
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

        new Thread(() -> {
            while (client.getGame() == null){
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            SwingUtilities.invokeLater(() -> {
                if (mapPanel == null) {
                    mapPanel = new MapPanel(client.getGame());
                    frame.getContentPane().removeAll();
                    frame.add(mapPanel, BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                } else {
                    mapPanel.setGame(client.getGame());
                }
                
                GameController gameController = new GameController(this, client.getGame(), mapPanel, client);
                mapPanel.setGameController(gameController);
            });
        }).start();
    }

    public static JFrame getFrame() {
        return frame;
    }

    public void winnerPanel(Player player) {

    }

    public void loadGame(){
        game = new Game(new Player("loadedGame", Color.RED));
        mapPanel = new MapPanel(game); 
        GameController gameController = new GameController(game, mapPanel); 
        mapPanel.setGameController(gameController);    
        gameController.loadGame("gameState.ser");

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

}