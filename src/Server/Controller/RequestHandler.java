package Server.Controller;

import Factories.DataStructureFactory;
import Interfaces.BuildingCallback;
import Interfaces.FileFoundCallback;
import Interfaces.IDataStructure;
import Server.Model.RequestTypes;

import java.io.*;
import java.util.Optional;
import java.util.StringTokenizer;

public class RequestHandler implements FileFoundCallback, BuildingCallback {
    private final BufferedWriter writer;
    private final BufferedOutputStream outputStream;
    private final BufferedReader reader;
    private IDataStructure structure;

    public RequestHandler(BufferedWriter writer, BufferedOutputStream outputStream, BufferedReader reader) {
        this.writer = writer;
        this.outputStream = outputStream;
        this.reader = reader;
    }

    public boolean handleRequest() throws IOException {
        String input = reader.readLine();
        StringTokenizer parse = new StringTokenizer(input);

        String method = parse.nextToken().toUpperCase();

        Optional<RequestTypes> type = RequestTypes.getRequestType(method);

        if(type.isPresent()){
            writer.write("HTTP/1.1 404 Bad request"); //autoFlush=true streams are closed in calling method
            return false;
        }

        return true;
    }

    public boolean requestIsHandled(){
        return structure.isDone();
    }

    @Override
    public synchronized void onFileFound(File file) {
        try {
            outputStream.write(getFileData(file));
            outputStream.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private byte[] getFileData(File file){
        FileInputStream fileIn = null;

        byte[] data = new byte[(int) file.length()];

        try{
            fileIn = new FileInputStream(file);
            fileIn.read(data);
            fileIn.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return data;
    }

    public void createStructure(File file){
        DataStructureFactory.buildEmptyStructure(structure).build(file, this);
    }

    @Override
    public void onBuilding(String name) {
        while (!structure.isDone()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        try {
            writer.write("HTTP/1.1 200 OK"); //autoFlush=true streams are closed in calling method
            writer.newLine();
            writer.write(name);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
