package org.academiadecodigo.bootcamp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private static final int PORT = 8181;
    private List<ClientHandler> cHandlers = Collections.synchronizedList(new ArrayList<ClientHandler>());


    public static void main(String[] args) {

            Server server = new Server();
            server.start();

    }

    public void start(){
        int numberOfConnections = 0;

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true){
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, numberOfConnections, this);
                cHandlers.add(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
                numberOfConnections++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String fullMessage){
        synchronized (cHandlers){
            for(int i = 0; i < cHandlers.size(); i++){
                cHandlers.get(i).sendMessage(fullMessage);
            }
        }
    }

    public void remove(ClientHandler clientHandler){
        cHandlers.remove(clientHandler);
    }

}
