package Server.Runner;

import Interfaces.FileFoundCallback;
import Interfaces.IDataStructure;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Client implements Runnable, FileFoundCallback {
    private final Socket socket;
    private IDataStructure structure;
    private final Timer timer = new Timer();

    public Client(Socket socket){
        this.socket = socket;
    }

    public void setStructure(IDataStructure structure) {
        this.structure = structure;
    }

    @Override
    public void run() {
        while(socket.isConnected()){
            //do stuff
        }
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
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, TIME_OUT);
    }
}
