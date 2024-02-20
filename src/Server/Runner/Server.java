package Server.Runner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {
    private final Executor executor;
    private final ServerSocket socket;

    public Server(int port) throws IOException {
        executor = Executors.newCachedThreadPool();
        socket = new ServerSocket(port);
    }

    public void run() throws IOException {

        while (!socket.isClosed()){
            Socket client = socket.accept();
            new Client(client);
        }
    }

}
