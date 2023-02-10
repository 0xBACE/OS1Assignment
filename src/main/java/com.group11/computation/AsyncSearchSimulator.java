package com.group11.computation;

import java.net.Socket;

public class AsyncSearchSimulator implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;

    public AsyncSearchSimulator(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    public void run() {
        System.out.println("SERVER STARTED");
        try {
            SearchSimulator.processClientRequest(clientSocket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("server stopped");
    }
}