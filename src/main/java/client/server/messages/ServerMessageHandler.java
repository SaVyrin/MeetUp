package client.server.messages;

import database.FriendsDatabase;
import exceptions.DBConnectException;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.Socket;

public class ServerMessageHandler extends AbstractMessageHandler {

    private String loggedInPerson;
    private ObservableList<String> pendingFriendRequests;
    private ObservableList<String> friends;
    private ObservableList<String> onlinePeople;

    public ServerMessageHandler(Socket socket) {
        super(socket);
    }

    @Override
    public void sendMessage(String message) {
        String messageToClient = "";
        switch (message) {
            case "online" -> messageToClient = onlinePeopleString();
            case "pending" -> messageToClient = pendingFriendRequestsString();
            case "friends" -> messageToClient = friendsString();
        }
        try {
            System.out.println("sent to client");
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to client");
            closeEverything();
        }
    }

    @Override
    public void receiveMessage(VBox vBox1, VBox vBox2, VBox vBox3) {
        new Thread(() -> {
            main:
            while (socket.isConnected()) {
                try {
                    System.out.println("received from client");
                    String messageFromClient = bufferedReader.readLine();

                    String[] parsed = messageFromClient.split(Command.SEPARATOR);

                    if (parsed[0].equals(Command.LOG_IN.getCommandString())) {
                        onlinePeople.add(parsed[1]);
                        loggedInPerson = parsed[1];
                        sendMessage("pending");
                        sendMessage("friends");
                    }
                    if (parsed[0].equals(Command.LOG_OUT.getCommandString())) {
                        onlinePeople.remove(parsed[1]);
                        loggedInPerson = null;
                    }
                    if (parsed[0].equals(Command.FRIEND_REQ.getCommandString())) {
                        // Check if accept request
                        String pendingTo = parsed[1].split("-")[1];
                        for (String pending : pendingFriendRequests) {
                            String pendingFrom = pending.split("-")[0];

                            if (pendingFrom.equals(pendingTo)) {
                                friends.add(pending);
                                pendingFriendRequests.remove(pending);

                                FriendsDatabase friendsDatabase = new FriendsDatabase();
                                String[] friendsToInsert = pending.split("-");
                                String firstFriendToInsert = friendsToInsert[0];
                                String secondFriendToInsert = friendsToInsert[1];
                                friendsDatabase.insertTwoFriends(firstFriendToInsert, secondFriendToInsert);
                                continue main;
                            }
                        }

                        // Add to pending if nothing to accept
                        String alternativeParsed = parsed[1].split("-")[1] + "-" + parsed[1].split("-")[0];
                        if (!pendingFriendRequests.contains(parsed[1])
                                && !friends.contains(parsed[1])
                                && !friends.contains(alternativeParsed)) {
                            pendingFriendRequests.add(parsed[1]);
                        }
                    }

                    System.out.println(messageFromClient);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from a client");
                    closeEverything();
                    break;
                } catch (DBConnectException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void setOnlinePeople(ObservableList<String> onlinePeople) {
        this.onlinePeople = onlinePeople;
        sendMessage("online");
    }

    @Override
    public void setPendingFriendRequests(ObservableList<String> pendingFriendRequests) {
        this.pendingFriendRequests = pendingFriendRequests;
        sendMessage("pending");
    }

    @Override
    public void setFriends(ObservableList<String> friends) {
        this.friends = friends;
        sendMessage("friends");
    }

    private String onlinePeopleString() {
        StringBuilder messageToClientBuilder = new StringBuilder();
        messageToClientBuilder.append(Command.ONLINE).append(Command.SEPARATOR);
        int count = 0;
        for (String person : onlinePeople) {
            messageToClientBuilder.append(person);
            if (count != onlinePeople.size() - 1) {
                messageToClientBuilder.append(Command.SEPARATOR);
            }
        }
        return messageToClientBuilder.toString();
    }

    private String pendingFriendRequestsString() {
        StringBuilder messageToClientBuilder = new StringBuilder();
        messageToClientBuilder.append(Command.FRIEND_REQ).append(Command.SEPARATOR);
        int count = 0;
        for (String pendingString : pendingFriendRequests) {
            String[] pending = pendingString.split("-");
            String pendingFrom = pending[0];
            String pendingTo = pending[1];
            if (loggedInPerson.equals(pendingTo)) {
                messageToClientBuilder.append(pendingFrom);
                if (count != pendingFriendRequests.size() - 1) {
                    messageToClientBuilder.append(Command.SEPARATOR);
                }
            }
            count++;
        }
        return messageToClientBuilder.toString();
    }

    private String friendsString() {
        StringBuilder messageToClientBuilder = new StringBuilder();
        messageToClientBuilder.append(Command.FRIENDS).append(Command.SEPARATOR);
        int count = 0;
        for (String friendsString : friends) {
            String[] friendsStringSplitted = friendsString.split("-");
            String friend1 = friendsStringSplitted[0];
            String friend2 = friendsStringSplitted[1];
            if (friend1.equals(loggedInPerson)) {
                messageToClientBuilder.append(friend2);
            }
            if (friend2.equals(loggedInPerson)) {
                messageToClientBuilder.append(friend1);
            }
            if (count != friends.size() - 1) {
                messageToClientBuilder.append(Command.SEPARATOR);
            }
        }
        return messageToClientBuilder.toString();
    }

    @Override
    public void close() {
        closeEverything();
    }
}