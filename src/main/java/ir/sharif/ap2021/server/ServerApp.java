package ir.sharif.ap2021.server;


import ir.sharif.ap2021.server.Controller.ServerSocketManager;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ServerApp {

    private static final Logger logger = LogManager.getLogger(ServerApp.class);

    public static void main(String[] args) throws IOException {
        ServerSocketManager serverSocketManager = null;
        try {
            serverSocketManager = new ServerSocketManager();
            serverSocketManager.start();
            System.out.println(">>Server Started");
            logger.info("Server Started");
        } catch (DatabaseDisconnectException e) {
            logger.error("An error with Database: " + e.getMessage());
            System.out.println("Server Could not connect to database so it is going to shutdown");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
