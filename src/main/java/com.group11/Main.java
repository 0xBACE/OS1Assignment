package com.group11;

import com.group11.servers.MultiThreadedServer;

class Main {
    public static void main(String[] args) {
        MultiThreadedServer server = new MultiThreadedServer(9000);
        new Thread(server).start();
    }
}