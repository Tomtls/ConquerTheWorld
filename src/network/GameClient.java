package network;

import model.Game;
import model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;



public class GameClient {

    private Player player;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private Socket socket;
    private Game game;

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
            
            new Thread(() -> receiveGameModel(objectIn)).start();
            System.out.println("Connected to server");

        } catch (UnknownHostException ue) {
            System.out.println("Kein DNS-Eintrag für " + ip);
        } catch (NoRouteToHostException e) {
            System.err.println("Nicht erreichbar " + ip);
        } catch (IOException e) {
            System.out.println("IO-Error");
        }
    }


    public void receiveGameModel(ObjectInputStream objectIn) {
        try {
            while (true) {
                this.game = (Game) objectIn.readObject();
                
                System.out.println("Spielmodell erfolgreich vom Server empfangen.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendMove(int from, int to){
        try {
            objectOut.writeObject(new int[]{from, to});
            objectOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Game getGame() {
        return this.game;
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
            System.out.println("Verbindung zum Server geschlossen.");
        } catch (IOException e) {
            System.err.println("Fehler beim Schließen der Verbindung: " + e.getMessage());
        }
    }

}
