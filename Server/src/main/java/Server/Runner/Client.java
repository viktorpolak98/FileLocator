package Server.Runner;

import LogicInterfaces.IDataStructure;
import Server.Controller.RequestHandler;
import Server.Controller.ServerClientController;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Client implements Runnable {
    private final Socket socket;
    private IDataStructure structure;
    private final Timer timer = new Timer();
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new PrintWriter(socket.getOutputStream(), true));
            BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());

            RequestHandler requestHandler = new RequestHandler(writer, outputStream, reader);

            requestHandler.assignRequest();
            //busy waiting: change later
            while (!requestHandler.requestIsHandled()){
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            reader.close();
            writer.close();
            outputStream.close();
            socket.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        restartTimer(TIME_OUT);
    }

    @Override
    public void run() {
        startTimer(TIME_OUT);
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
