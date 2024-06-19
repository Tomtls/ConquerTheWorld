package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;

import model.Game;
import model.Player;

public class GameClient {

    private Player player;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private Game game;
    private Socket socket;

    public GameClient(Player player) {
        this.player = player;
    }

    public void connect(String ip) {
        System.out.println("Client gestartet!");

        int port = 8080; // Port-Nummer

        try  {
            socket = new Socket(ip, port);
            objectOut = new ObjectOutputStream(socket.getOutputStream()); // ObjectOutputstream zum Server
            objectIn = new ObjectInputStream(socket.getInputStream()); // ObjectInputstream vom Server
            
            new Thread(() -> receiveGameState(objectIn)).start();

        } catch (UnknownHostException ue) {
            System.out.println("Kein DNS-Eintrag für " + ip);
        } catch (NoRouteToHostException e) {
            System.err.println("Nicht erreichbar " + ip);
        } catch (IOException e) {
            System.out.println("IO-Error");
        }
    }

    private void receiveGameState(ObjectInputStream objectIn) {
        try {
            while (true) {
                game = (Game) objectIn.readObject(); // Empfangen des Spielzustands vom Server
                System.out.println("Aktueller Spielzustand empfangen ");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Game getGame() {
        return game;
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
