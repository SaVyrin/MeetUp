package client.server.messages;

import java.io.*;
import java.net.Socket;

public abstract class AbstractMessageHandler {

    public final Socket socket;
    public ObjectInputStream objectInputStream;
    public ObjectOutputStream objectOutputStream;

    public AbstractMessageHandler(Socket socket) {
        this.socket = socket;
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            this.objectOutputStream = new ObjectOutputStream(outputStream);
            this.objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating MessageHandler");
        }
    }

    public void closeEverything() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
                objectOutputStream = null;
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
                objectInputStream = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error closing everything");
        }
    }

    public boolean isClosed(){
        return socket.isClosed();
    }
}