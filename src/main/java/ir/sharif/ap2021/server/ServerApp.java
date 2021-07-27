package ir.sharif.ap2021.server;


import ir.sharif.ap2021.server.Controller.ServerSocketManager;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        ServerSocketManager serverSocketManager = null;
        try {
            serverSocketManager = new ServerSocketManager();
            serverSocketManager.start();
            System.out.println(">>Server Started");
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }
    }
}
