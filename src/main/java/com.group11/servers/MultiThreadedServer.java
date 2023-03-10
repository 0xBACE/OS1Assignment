package com.group11.servers;

import com.group11.computation.AsyncSearchSimulator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer implements Runnable{

    protected int serverPort = 8080;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread       runningThread= null;

    public MultiThreadedServer(int port) {
        this.serverPort = port;
    }

    public void run() {
        openServerSocket();
        while (!isStopped()) {
            synchronized(this){
                this.runningThread = Thread.currentThread();
            }
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }

            // on receiving a request, execute the heavy computation in a new thread
            new Thread(
                    new AsyncSearchSimulator(
                            clientSocket,
                            "Multithreaded Server"
                    )
            ).start();
        }

        System.out.println("Server Stopped.");
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.serverPort, e);
        }
    }
}