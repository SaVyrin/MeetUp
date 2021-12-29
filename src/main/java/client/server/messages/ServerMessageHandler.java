package client.server.messages;

import acquaintance.Person;
import database.FriendsDatabase;
import exceptions.DBConnectException;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMessageHandler extends AbstractMessageHandler {

    private Person loggedInPerson;
    private ObservableList<String> pendingFriendRequests;
    private ObservableList<String> friends;
    private ObservableList<String> onlinePeople;

    public ServerMessageHandler(Socket socket) {
        super(socket);
    }

    public void sendMessage(String message) {
        Message messageToClient = new Message(loggedInPerson);
        switch (message) {
            case "online" -> onlinePeopleMessage(messageToClient);
            case "pending" -> pendingFriendRequestsMessage(messageToClient);
            case "friends" -> friendsMessage(messageToClient);
        }
        try {
            System.out.println("sent to client");
            objectOutputStream.writeObject(messageToClient);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to client");
            closeEverything();
        }
    }

    public void receiveMessage() {
        new Thread(() -> {
            main:
            while (socket.isConnected()) {
                try {
                    System.out.println("received from client");
                    Message messageFromClient = (Message) objectInputStream.readObject();

                    Command command = messageFromClient.getCommand();
                    Person loggedInPerson = messageFromClient.getLoggedInPerson();
                    String message = messageFromClient.getMessage();

                    if (command.equals(Command.LOG_IN)) {
                        this.loggedInPerson = messageFromClient.getLoggedInPerson();
                        onlinePeople.add(loggedInPerson.getLogin());
                        sendMessage("pending");
                        sendMessage("friends");
                    }
                    if (command.equals(Command.LOG_OUT)) {
                        onlinePeople.remove(loggedInPerson.getLogin());
                        this.loggedInPerson = null;
                        closeEverything();
                    }
                    if (command.equals(Command.FRIEND_REQ)) {
                        // Check if accept request
                        String parsedFrom = message.split("-")[0];
                        String parsedTo = message.split("-")[1];
                        for (String pending : pendingFriendRequests) {
                            String pendingFrom = pending.split("-")[0];
                            String pendingTo = pending.split("-")[1];

                            if (parsedTo.equals(pendingFrom) && parsedFrom.equals(pendingTo)) {
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
                        String alternativeParsed = message.split("-")[1] + "-" + message.split("-")[0];
                        if (!pendingFriendRequests.contains(message)
                                && !friends.contains(message)
                                && !friends.contains(alternativeParsed)) {
                            pendingFriendRequests.add(message);
                        }
                    }

                    System.out.println(messageFromClient);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from a client");
                    closeEverything();
                    break;
                } catch (DBConnectException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setOnlinePeople(ObservableList<String> onlinePeople) {
        this.onlinePeople = onlinePeople;
    }
    public void noticeOnlinePeople(ObservableList<String> onlinePeople) {
        setOnlinePeople(onlinePeople);
        sendMessage("online");
    }

    public void setPendingFriendRequests(ObservableList<String> pendingFriendRequests) {
        this.pendingFriendRequests = pendingFriendRequests;
    }
    public void noticePendingFriendRequests(ObservableList<String> pendingFriendRequests) {
        setPendingFriendRequests(pendingFriendRequests);
        sendMessage("pending");
    }

    public void setFriends(ObservableList<String> friends) {
        this.friends = friends;
    }
    public void noticeFriends(ObservableList<String> friends) {
        setFriends(friends);
        sendMessage("friends");
    }

    private void onlinePeopleMessage(Message messageToClient) {
        messageToClient.setCommand(Command.ONLINE);
        List<String> onlinePeopleToSend = new ArrayList<>(onlinePeople);
        messageToClient.setOnlinePeople(onlinePeopleToSend);
    }

    private void pendingFriendRequestsMessage(Message messageToClient) {
        messageToClient.setCommand(Command.FRIEND_REQ);

        List<String> pendingToSend = new ArrayList<>();
        for (String pendingString : pendingFriendRequests) {
            String[] pending = pendingString.split("-");
            String pendingTo = pending[1];
            if (pendingTo.equals(loggedInPerson.getLogin())) {
                pendingToSend.add(pendingString);
            }
        }
        messageToClient.setPendingFriendRequests(pendingToSend);
    }

    private void friendsMessage(Message messageToClient) {
        messageToClient.setCommand(Command.FRIENDS);

        List<String> friendsToSend = new ArrayList<>();
        for (String friendsString : friends) {
            String[] friendsStringSplitted = friendsString.split("-");
            String friend1 = friendsStringSplitted[0];
            String friend2 = friendsStringSplitted[1];

            if (friend1.equals(loggedInPerson.getLogin())) {
                friendsToSend.add(friend2);
            }
            if (friend2.equals(loggedInPerson.getLogin())) {
                friendsToSend.add(friend1);
            }
        }
        messageToClient.setFriends(friendsToSend);
    }
}