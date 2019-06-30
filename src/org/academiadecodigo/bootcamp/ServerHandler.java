package org.academiadecodigo.bootcamp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerHandler implements Runnable{

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            BufferedReader messageReceiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (socket.isConnected()) {

                String messageReceived = messageReceiver.readLine();
                if (messageReceived != null) {
                    System.out.println(messageReceived);
                }

            }

            messageReceiver.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
