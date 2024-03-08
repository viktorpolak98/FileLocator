package Server.Runner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final ServerSocket socket;
    private final Map<String, Client> activeClients = new HashMap<>();

    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
        run();
    }

    public void run() throws IOException {
        while (!socket.isClosed()){
            Socket clientSocket = socket.accept();
            String clientAddress = clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort();
            if (activeClients.containsKey(clientAddress)){
                activeClients.get(clientAddress).handleRequest();
            }
            else {
                Client client = new Client(clientSocket, clientAddress, this);
                activeClients.putIfAbsent(clientAddress, client);
                new Thread(client).start();
            }
        }
    }

    public void removeClient(String clientAddress){
        activeClients.remove(clientAddress);
    }
}
