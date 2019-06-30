package org.academiadecodigo.bootcamp;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket clientSocket;
    private Server server;
    private BufferedReader in;
    private PrintWriter out;
    private String name;

    public ClientHandler(Socket clientSocket, int number, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        name = "ChatClient-" + number + ": ";
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (clientSocket.isConnected()) {
            try {
                String message = in.readLine();
                if (message.equals(null)) {
                    in.close();
                    clientSocket.close();
                    continue;
                }
                if (message.split(" ")[0].contains("/quit")){break;}
                if (message.split(" ")[0].contains("/alias")){ name = message.substring(message.indexOf(" ") + 1) + " : "; continue;}
                server.broadcastMessage(name + message);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        try {
            in.close();
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        server.remove(this);
    }

    public void sendMessage(String fullMessage){
        out.print(fullMessage + "\n");
        out.flush();
    }

}