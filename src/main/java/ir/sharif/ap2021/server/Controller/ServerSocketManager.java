package ir.sharif.ap2021.server.Controller;


import ir.sharif.ap2021.server.Config.NetworkConfig;
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
    private final List<ClientHandler> clientHandlers;
    private final NetworkConfig networkConfig;
    private volatile boolean running;

    public ServerSocketManager() throws IOException, DatabaseDisconnectException {

        networkConfig = new NetworkConfig();
        connector = new Connector();
        serverSocket = new ServerSocket(Integer.parseInt(networkConfig.getPort()));
        running = true;
        clientHandlers = Collections.synchronizedList(new ArrayList<>());
    }

    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public void start() {
        new Thread(this::getOrders).start();
        new Thread(this::accept).start();
    }

    private void accept() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                ResponseSender responseSender = new SocketResponseSender(this, socket);
                ClientHandler clientHandler = new ClientHandler(responseSender, connector, this);
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
            System.out.println(">>type exit to shutdown the server but first make sure that no client is connected");
            System.out.println(">>type add bot to add awesome bots");
            switch (scanner.nextLine().strip()) {
                case "exit" -> {
                    for (ClientHandler clientHandler : clientHandlers) {
                        clientHandler.exit();
                    }
                    running = false;
                    connector.close();
                    try {
                        serverSocket.close();
                    } catch (IOException ignored) {
                    }
                    System.exit(0);
                }
                case "number" -> {
                    System.out.println(clientHandlers.size());
                }
                case "add bot" -> {
                    try {
                        System.out.println(">>enter bot name");
                        String botName = scanner.nextLine().strip();
                        System.out.println(">>enter jar url");
                        String jarUrl = scanner.nextLine().strip();
                        System.out.println(">>enter main class name");
                        String className = scanner.nextLine().strip();
//                        botLoader.loadBot(botName, jarUrl, className);
                        System.out.println(">>successfully added");
                    } catch (Throwable e) {
                        System.out.println(">>cant load bot");
                        e.printStackTrace(System.out);
                    }
                }
                default -> System.out.println("unknown command");
            }
        }
    }
}
