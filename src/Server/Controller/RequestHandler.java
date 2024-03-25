package Server.Controller;

import Interfaces.FileFoundCallback;
import Interfaces.IDataStructure;
import Server.Model.RequestTypes;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.StringTokenizer;

public class RequestHandler implements FileFoundCallback{
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
    
    //TODO: Implement
    public boolean requestIsHandled(){
        return true;
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
}
