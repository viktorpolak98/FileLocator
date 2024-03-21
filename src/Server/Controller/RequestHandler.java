package Server.Controller;

import Interfaces.IDataStructure;
import Server.Model.RequestTypes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Optional;

public class RequestHandler {
    private final BufferedWriter writer;
    private final BufferedReader reader;
    private final IDataStructure structure;

    public RequestHandler(BufferedWriter writer, BufferedReader reader, IDataStructure structure) {
        this.writer = writer;
        this.reader = reader;
        this.structure = structure;
    }

    public static boolean handleRequest(Optional<RequestTypes> type){


        return true;
    }

}
