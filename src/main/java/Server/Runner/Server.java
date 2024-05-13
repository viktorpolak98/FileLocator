package main.java.Server.Runner;

import main.java.Server.Controller.ServerClientController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final ServerSocket socket;
    private final Map<String, Client> activeClients = new HashMap<>();
    private final ServerClientController controller;

    public Server(int port, ServerClientController controller) throws IOException {
        socket = new ServerSocket(port);
        this.controller = controller;

        try {
            run();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        while (!socket.isClosed()){
            Socket clientSocket = socket.accept();
            String clientAddress = clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort();
            if (activeClients.containsKey(clientAddress)){
                activeClients.get(clientAddress).receiveRequest();
            }
            else {
                Client client = new Client(clientSocket, clientAddress, controller);
                activeClients.putIfAbsent(clientAddress, client);
                new Thread(client).start();
            }
        }
    }

    public void removeClient(String clientAddress){
        activeClients.remove(clientAddress);
    }
}
