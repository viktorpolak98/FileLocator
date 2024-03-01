package Server.Runner;

import Server.Model.RequestTypes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {
    private final ServerSocket socket;

    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    public void run() throws IOException {
        while (!socket.isClosed()){
            Socket client = socket.accept();
            new Client(client);
        }
    }
}
