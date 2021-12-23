package client.server.messages;

import java.io.*;
import java.net.Socket;

public abstract class AbstractMessageHandler implements MessageHandler {

    public final Socket socket;
    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;

    public AbstractMessageHandler(Socket socket) {
        this.socket = socket;
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
}
