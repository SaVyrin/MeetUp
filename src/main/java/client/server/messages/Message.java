package client.server.messages;

import acquaintance.Person;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    private Person loggedInPerson;
    private Command command;
    private String message;
    private List<String> onlinePeople;
    private List<String> pendingFriendRequests;
    private List<String> friends;
    private Person show;

    public Message(Person loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
    }

    public Person getLoggedInPerson() {
        return loggedInPerson;
    }

    public void setLoggedInPerson(Person loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getOnlinePeople() {
        return onlinePeople;
    }

    public void setOnlinePeople(List<String> onlinePeople) {
        this.onlinePeople = onlinePeople;
    }

    public List<String> getPendingFriendRequests() {
        return pendingFriendRequests;
    }

    public void setPendingFriendRequests(List<String> pendingFriendRequests) {
        this.pendingFriendRequests = pendingFriendRequests;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public Person getShow() {
        return show;
    }

    public void setShow(Person show) {
        this.show = show;
    }
}
