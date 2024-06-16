package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.Game;
import model.State;

public class GameServer {
    
    public GameServer(Game game) {
        int port = 8080;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server gestartet!");

            while (true) {
                try (Socket socket = server.accept()) {

                    ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
                    PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);

                    Object obj = objectIn.readObject();
                    if (obj instanceof ArrayList) {
                        //eingabe vom Client
                        State[] states = (State[]) obj;
                        System.out.println("Client: " + states);
                        
                        // Do something with eingabe
                        game.moveUnits(states[0], states[1]);
                    }

                    socketOut.println("States received");

                } catch (IOException | ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }

                System.out.println("Warte auf nächste Anfrage!");
            }
        } catch (IOException e) {
            System.out.println("Server konnte nicht gestartet werden!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
