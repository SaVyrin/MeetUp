package connections;

import acquaintance.Person;
import client.server.Client;
import exceptions.ServerConnectException;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection implements Connection<Client> {

    private Person loggedInPerson;

    public ServerConnection(Person loggedInPerson){
        this.loggedInPerson = loggedInPerson;
    }

    @Override
    public Client connect() throws ServerConnectException {
        try {
            return new Client(new Socket("localhost", 9999), loggedInPerson);
        } catch (IOException e) {
            throw new ServerConnectException("Error connecting to server");
        }
    }
}
