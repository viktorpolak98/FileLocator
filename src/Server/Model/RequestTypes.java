package Server.Model;

import java.util.Optional;

public enum RequestTypes {
    GET,
    POST,
    PUT;

    public static Optional<RequestTypes> getRequestType(String request){
        return switch (request){
            case "GET" -> Optional.of(GET);
            case "POST" -> Optional.of(POST);
            case "PUT" -> Optional.of(PUT);
            default -> Optional.empty();
        };
    }
}
