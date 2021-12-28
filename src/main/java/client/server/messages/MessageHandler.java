package client.server.messages;

import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public interface MessageHandler {

    void sendMessage(String message);

    void receiveMessage(VBox vBox1, VBox vBox2, VBox vBox3);

    void setOnlinePeople(ObservableList<String> onlinePeople);

    void setPendingFriendRequests(ObservableList<String> pendingFriendRequests);

    void setFriends(ObservableList<String> friends);

    void close();

    boolean isConnected();

}
