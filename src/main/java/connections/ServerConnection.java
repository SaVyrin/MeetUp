package connections;

import client.server.Client;
import exceptions.ServerConnectException;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection implements Connection<Client> {

    @Override
    public Client connect() throws ServerConnectException {
        try {
            return new Client(new Socket("localhost", 9999));
        } catch (IOException e) {
            throw new ServerConnectException("Error connecting to server");
        }
    }
}
