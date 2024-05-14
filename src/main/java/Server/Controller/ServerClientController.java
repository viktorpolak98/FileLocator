package Server.Controller;

import Server.Runner.Server;


public class ServerClientController {
    private final Server server;

    public ServerClientController(Server server){
        this.server = server;
    }

    public void removeClient(String client){
        server.removeClient(client);
    }
}
