package client.server.messages;

import java.io.*;
import java.net.Socket;

public abstract class AbstractMessageHandler {

    public final Socket socket;
    public BufferedReader bufferedReader;
    public ObjectInputStream objectInputStream;
    public BufferedWriter bufferedWriter;
    public ObjectOutputStream objectOutputStream;

    public AbstractMessageHandler(Socket socket) {
        this.socket = socket;
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

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
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error closing everything");
        }
    }

    public boolean isConnected(){
        return socket.isConnected();
    }
}