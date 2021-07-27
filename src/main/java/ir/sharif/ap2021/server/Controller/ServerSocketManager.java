package ir.sharif.ap2021.server.Controller;


import ir.sharif.ap2021.server.Controller.Network.ResponseSender;
import ir.sharif.ap2021.server.Controller.Network.SocketResponseSender;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ServerSocketManager {

    private final ServerSocket serverSocket;
    private final Connector connector;
    //    private final ModelLoader modelLoader;
    private final List<ClientHandler> clientHandlers;
    private volatile boolean running;

    public ServerSocketManager() throws IOException, DatabaseDisconnectException {
//        Config config = ConfigFactory.getInstance().getConfig("SERVER_CONFIG");
//        int port = config.getProperty(Integer.class, "PORT");
//        connector = new Connector(ConfigFactory.getInstance().getConfigFile("SERVER_HIBERNATE_CONFIG")
//                , System.getenv("HearthStone password"));
        connector = new Connector();
//        modelLoader = new ModelLoader(connector);
        int port = 8000;
        serverSocket = new ServerSocket(port);
        running = true;
        clientHandlers = Collections.synchronizedList(new ArrayList<>());
//        gameLoader = new GameLoader();
    }

    public void start() {
        new Thread(this::getOrders).start();
        new Thread(this::accept).start();
    }

    private void accept() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                ResponseSender responseSender = new SocketResponseSender(this,socket);
                ClientHandler clientHandler = new ClientHandler(responseSender,connector);
                clientHandlers.add(clientHandler);
                clientHandler.start();
                System.out.println(">>New Client Added");
            } catch (IOException ignore) {
            }
        }
    }

    public void removeClientHandler(SocketResponseSender socketResponseSender) {
        clientHandlers.removeIf(clientHandler -> clientHandler.getResponseSender().equals(socketResponseSender));
    }

    private void getOrders() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println(">>type commands");
            System.out.println(">>type exit to shutdown server. make sure no client connected");
            System.out.println(">>type add game to add game");
            switch (scanner.nextLine().strip()) {
                case "exit" -> {
                    for (ClientHandler clientHandler : clientHandlers) {
//                        try {
                        clientHandler.exit();
//                        } catch (DatabaseDisconnectException ignored) {
//                        }
                    }
                    running = false;
//                    connector.close();
                    try {
                        serverSocket.close();
                    } catch (IOException ignored) {
                    }
                    System.exit(0);
                }
                case "add game" -> {
                    try {
                        System.out.println(">>enter game name");
                        String gameName = scanner.nextLine().strip();
                        System.out.println(">>enter jar url");
                        String jarUrl = scanner.nextLine().strip();
                        System.out.println(">>enter main class name");
                        String className = scanner.nextLine().strip();
//                        gameLoader.loadGame(gameName, jarUrl, className, gameLobby);
                        System.out.println(">>successfully added");
                    } catch (Throwable e) {
                        System.out.println(">>cant load game");
                        e.printStackTrace(System.out);
                    }
                }
                default -> System.out.println("unknown command");
            }
        }
    }
}
