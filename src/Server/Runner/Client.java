package Server.Runner;

import Interfaces.FileFoundCallback;
import Interfaces.IDataStructure;
import Server.Controller.RequestHandler;
import Server.Controller.ServerClientController;
import Server.Model.RequestTypes;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Client implements Runnable, FileFoundCallback {
    private final Socket socket;
    private IDataStructure structure;
    private final Timer timer = new Timer();
    private BufferedReader reader;
    private BufferedWriter writer;
    private final String clientAddress;
    private final ServerClientController controller;
    private final long TIME_OUT = 500_000;

    public Client(Socket socket, String clientAddress, ServerClientController controller){
        this.socket = socket;
        this.clientAddress = clientAddress;
        this.controller = controller;

    }

    public void setStructure(IDataStructure structure) {
        this.structure = structure;
    }

    public void receiveRequest(){
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new PrintWriter(socket.getOutputStream(), true));

            String input = reader.readLine();
            StringTokenizer parse = new StringTokenizer(input);

            String method = parse.nextToken().toUpperCase();

            RequestHandler.handleRequest(RequestTypes.getRequestType(method));

        } catch (IOException e){
            e.printStackTrace();
        }

        restartTimer(TIME_OUT);
    }

    @Override
    public void run() {
        startTimer(TIME_OUT);
    }

    public Vector<File> search(String searchQuery){
        return structure.search(searchQuery, this);
    }
    @Override
    public void onFileFound(File file) {
        //Send data to client
    }
    public boolean restartTimer(long TIME_OUT){
        timer.cancel();
        try {
            startTimer(TIME_OUT);
        } catch (RuntimeException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void startTimer(long TIME_OUT) throws RuntimeException {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    socket.close();
                    controller.removeClient(clientAddress);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, TIME_OUT);
    }
}
