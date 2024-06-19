package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import model.Game;


public class GameServer implements Runnable {
    private List<ClientHandler> clients = new ArrayList<>();
    private Game game;
    
    public GameServer(Game game) {
        this.game = game;
    }
    @Override
    public void run() {
        int port = 8080;

            try (ServerSocket server = new ServerSocket(port)) {
                System.out.println("Server gestartet!");
                
                while (true) {
                    Socket socket = server.accept();
                    System.out.println("Client verbunden!");
                    GameController.timer.start();
                    ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                    objectOut.writeObject(game);
                    objectOut.flush();
                   
                    ClientHandler clientHandler = new ClientHandler(socket, this);
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();
                }
            } catch (IOException e) {
                System.out.println("Server konnte nicht gestartet werden!");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
    }

    public void broadcastGameState(){
        for (ClientHandler client : clients) {
            try {
                client.sendGameState(game);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

    private class ClientHandler implements Runnable {
        private Socket socket;
        private GameServer server;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientHandler(Socket socket, GameServer server) {
            this.socket = socket;
            this.server = server;
            try {
                this.out = new ObjectOutputStream(socket.getOutputStream());
                this.in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    //empfang von Daten von Clients
                    Object obj = in.readObject(); 
                    server.broadcastGameState();

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        public void sendGameState(Game game) throws IOException {
            out.writeObject(game);
            out.flush();
        }
    }
}
