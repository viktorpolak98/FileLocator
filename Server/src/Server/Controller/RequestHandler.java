package Server.Controller;

import LogicFactories.DataStructureFactory;
import LogicInterfaces.IDataStructure;
import LogicInterfaces.BuildingCallback;
import LogicInterfaces.FileFoundCallback;
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

    public boolean assignRequest() throws IOException {
        String input = reader.readLine();
        StringTokenizer parse = new StringTokenizer(input);

        String method = parse.nextToken().toUpperCase();

        Optional<RequestTypes> type = RequestTypes.getRequestType(method);



        return assignRequest(type.orElse(null));
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

    private boolean validateRequest(RequestTypes type) throws IOException {
        if(type == null) {
            writer.write("HTTP/1.1 404 Bad request"); //autoFlush=true streams are closed in calling method
            return false;
        }
        return true;
    }

    private boolean assignRequest(RequestTypes type) throws IOException {
        if (!validateRequest(type)){
            return false;
        }
        switch (type){
            case POST -> createStructure();
            case GET -> getSearchResult();
        }

        return true;
    }

    private void getSearchResult() throws IOException {
        String searchQuery = reader.readLine();
        structure.search(searchQuery, this);
    }

    public void createStructure() throws IOException {
        String directoryName = reader.readLine();
        String structureType = reader.readLine();
        File root = new File(directoryName);
//        receiveDirectory(root);
        structure = DataStructureFactory.buildEmptyStructure(structureType);
        structure.build(root, this);
    }

    private void receiveDirectory(File directory) throws IOException {

        directory.mkdirs();

        int numEntries = Integer.parseInt(reader.readLine());

        for (int i = 0; i < numEntries; i++) {
            String entryName = reader.readLine();

            if (entryName == null || entryName.startsWith(".")) {
                continue;
            }

            File entry = new File(directory, entryName);

            if (entry.isDirectory()) {
                receiveDirectory(entry);
            }
        }
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
