package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import model.Game;
import model.Player;

public class GameServer {
    private Game game;
    private ServerSocket serverSocket;
    private List<ObjectOutputStream> clientOutputs;
    private int connectedClients = 0;
    private Player[] players;
    
    public GameServer() {
        clientOutputs = new ArrayList<>();
        players = new Player[2];
    }
    public void start(){
        int port = 8080;
        	try {
                serverSocket = new ServerSocket(port);
                System.out.println("Server started");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected");

                    ObjectOutputStream objactOut = new ObjectOutputStream(clientSocket.getOutputStream());
                    clientOutputs.add(objactOut);
                    connectedClients++;
                    if (connectedClients == 2) {
                        	initializeGame();
                    }

                    new Thread(() -> handleClient(clientSocket, objactOut)).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void handleClient(Socket clientSocket, ObjectOutputStream objactOut) {
        try {
            ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream());
            Player player = (Player) objectIn.readObject();
            synchronized (this) {
                players[connectedClients -1] = player;
 
            }

            while (true) {
                Object obj = objectIn.readObject();
                if (obj instanceof int[]) {
                    int[] move = (int[]) obj;
                    handleMove(move[0], move[1]);
                    sendGameStateToClients();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeGame() {
        game = new Game(players);
        sendGameStateToClients();
        startUnitUpdateTimer();
        System.out.println("Spiel initialisiert");
    }

    private void handleMove(int sourceId, int targetId) {
        game.moveUnits(sourceId, targetId);
        System.out.println("Bewegung verarbeitet: von " + sourceId + " nach " + targetId);
    }

    private void sendGameStateToClients() {
        for (ObjectOutputStream out : clientOutputs) {
            try {
                out.writeObject(game);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startUnitUpdateTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                game.updateUnits();
                sendGameStateToClients();
            }
        }, 0, 1000); 
        System.out.println("Timer gestartet!"); 
    }

}
