package connections;

import client.server.Client;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection implements Connection<Client>{

    @Override
    public Client connect() {
        try {
            return new Client(new Socket("localhost", 9999));
        } catch (IOException e) {
            System.out.println("Can not connect to server");
        }
        return null;
    }
}
