package client.server.messages;

import javafx.scene.control.TextArea;

public interface MessageHandler {

    void sendMessage(String message);

    void receiveMessage(TextArea textArea);

}
