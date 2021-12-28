package client.server;

import client.server.messages.MessageHandler;
import client.server.messages.ServerMessageHandler;
import database.FriendsDatabase;
import exceptions.DBConnectException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final ServerSocket serverSocket;

    private final ObservableList<String> pendingFriendRequests = FXCollections.observableArrayList();
    private final ObservableList<String> onlinePeople = FXCollections.observableArrayList();
    private final ObservableList<String> friends = FXCollections.observableArrayList();
    private final List<MessageHandler> messageHandlers = new ArrayList<>();

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            Server server = new Server(port);
            server.start();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot start the server", e);
        } catch (DBConnectException e) {
            e.printStackTrace();
        }
    }

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void start() throws DBConnectException {
        FriendsDatabase friendsDatabase = new FriendsDatabase();
        List<List<String>> friendsListFromDB = friendsDatabase.getAllFriendsFromDB();
        for (List<String> twoFriends : friendsListFromDB) {
            String firstFriend = twoFriends.get(0);
            String secondFriend = twoFriends.get(1);
            friends.add(firstFriend + "-" + secondFriend);
        }

        onlinePeople.addListener((ListChangeListener<String>) change -> {
            removeInactiveMessageHandlers();
            setOnlinePeopleToMessageHandlers();
        });

        pendingFriendRequests.addListener((ListChangeListener<String>) change -> {
            setPendingFriendRequestsToMessageHandlers();
        });
        friends.addListener((ListChangeListener<String>) change -> {
            setFriendsToMessageHandlers();
        });

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected from: " + socket.getInetAddress());

                MessageHandler messageHandler = new ServerMessageHandler(socket);
                messageHandlers.add(messageHandler);

                messageHandler.receiveMessage(null, null, null);
                messageHandler.setOnlinePeople(onlinePeople);
                messageHandler.setPendingFriendRequests(pendingFriendRequests);
                messageHandler.setFriends(friends);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error creating connection");
            }
        }
    }

    private void setOnlinePeopleToMessageHandlers() {
        for (MessageHandler messageHandler : messageHandlers) {
            messageHandler.setOnlinePeople(onlinePeople);
        }
    }

    private void setPendingFriendRequestsToMessageHandlers() {
        for (MessageHandler messageHandler : messageHandlers) {
            messageHandler.setPendingFriendRequests(pendingFriendRequests);
        }
    }

    private void setFriendsToMessageHandlers() {
        for (MessageHandler messageHandler : messageHandlers) {
            messageHandler.setFriends(friends);
        }
    }

    private void removeInactiveMessageHandlers() {
        messageHandlers.removeIf(messageHandler -> !messageHandler.isConnected());
    }
}
