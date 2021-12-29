package client.server.messages;

import acquaintance.Person;
import database.FriendsDatabase;
import exceptions.DBConnectException;
import client.server.PersonToShowChooser;
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

    private PersonToShowChooser personToShowChooser;

    public ServerMessageHandler(Socket socket) {
        super(socket);
    }

    public void sendMessage(String message) {
        Message messageToClient = new Message(loggedInPerson);
        switch (message) {
            case "online" -> onlinePeopleMessage(messageToClient);
            case "pending" -> pendingFriendRequestsMessage(messageToClient);
            case "friends" -> friendsMessage(messageToClient);
            case "show" -> showMessage(messageToClient);
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
                        this.personToShowChooser = new PersonToShowChooser(loggedInPerson);
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
                        String pendFrom = loggedInPerson.getLogin();
                        String pendTo = personToShowChooser.getCurrShownPerson().getLogin();

                        String pend = pendFrom + "-" + pendTo;
                        for (String pending : pendingFriendRequests) {
                            String pendingFrom = pending.split("-")[0];
                            String pendingTo = pending.split("-")[1];

                            if (pendTo.equals(pendingFrom) && pendFrom.equals(pendingTo)) {
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
                        String alternativeParsed = pendTo + "-" + pendFrom;
                        if (!pendingFriendRequests.contains(pend)
                                && !friends.contains(pend)
                                && !friends.contains(alternativeParsed)) {
                            pendingFriendRequests.add(pend);
                        }
                    }

                    if (command.equals(Command.SHOW)) {
                        switch (message) {
                            case "LEFT" -> personToShowChooser.leftChanger();
                            case "RIGHT" -> personToShowChooser.rightChanger();
                            case "FRIEND" -> personToShowChooser.chooseFriends();
                            case "COUPLE" -> personToShowChooser.chooseCouples();
                        }
                        sendMessage("show");
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
            String pendingFrom = pending[0];
            String pendingTo = pending[1];
            if (pendingTo.equals(loggedInPerson.getLogin())) {
                pendingToSend.add(pendingFrom);
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

    private void showMessage(Message messageToClient) {
        messageToClient.setCommand(Command.SHOW);
        Person personToShowSend = personToShowChooser.getCurrShownPerson();
        messageToClient.setShow(personToShowSend);
    }
}