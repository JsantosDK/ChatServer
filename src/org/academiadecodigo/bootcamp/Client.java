package org.academiadecodigo.bootcamp;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket socket;

    public static void main(String[] args) {

        Client client = new Client();
    }

    public Client() {
        try {
            socket = new Socket("localhost", 8181);
            start();
        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        Thread thread = new Thread(new ServerHandler(socket));
        thread.start();
        try {

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String consoleInput;

            while (socket.isConnected()) {

                    consoleInput = input.readLine();

                if (consoleInput.equals("/quit")) {

                    input.close();
                    output.close();
                    thread.interrupt();
                    break;
                }


                output.write(consoleInput + "\n");
                output.flush();
            }

            input.close();
            output.close();
            socket.close();

        } catch (IOException e){
            e.printStackTrace();}
    }

}