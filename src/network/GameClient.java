package network;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;

import model.State;

public class GameClient {
    private State[] states;
    private String name;
    private Color color;

    public GameClient(State[] states) {
        this.states = states;
    }
    public GameClient(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void sendPoints(String hostName) {
        System.out.println("Client gestartet!");

        int port = 8080; // Port-Nummer

        try (Socket socket = new Socket(hostName, port)) {
            ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream()); // ObjectOutputstream zum Server
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Inputstream vom Server

            objectOut.writeObject(states); // Sende ArrayList an den Server

            String response = socketIn.readLine(); // Zeile vom Server empfangen
            System.out.println("Server: "+response); // Zeile auf die Konsole schreiben

        } catch (UnknownHostException ue) {
            System.out.println("Kein DNS-Eintrag für " + hostName);
        } catch (NoRouteToHostException e) {
            System.err.println("Nicht erreichbar " + hostName);
        } catch (IOException e) {
            System.out.println("IO-Error");
        }
    }

}
