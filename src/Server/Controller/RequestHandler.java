package Server.Controller;

import Interfaces.FileFoundCallback;
import Interfaces.IDataStructure;
import Server.Model.RequestTypes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.StringTokenizer;

public class RequestHandler {
    private final BufferedWriter writer;
    private final BufferedReader reader;
    private IDataStructure structure;

    public RequestHandler(BufferedWriter writer, BufferedReader reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public boolean handleRequest() throws IOException {
        String input = reader.readLine();
        StringTokenizer parse = new StringTokenizer(input);

        String method = parse.nextToken().toUpperCase();

        Optional<RequestTypes> type = RequestTypes.getRequestType(method);

        if(type.isPresent()){
            writer.write("HTTP/1.1 404 Bad request");
            return false;
        }

        return true;
    }


}
