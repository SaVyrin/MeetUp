package client.server.messages;

public enum Command {
    LOG_IN("LOG_IN"),
    LOG_OUT("LOG_OUT"),
    FRIEND_REQ("FRIEND_REQ"),
    ONLINE("ONLINE"),
    FRIENDS("FRIENDS");

    public static final String SEPARATOR = ":";

    private final String commandString;

    Command(String commandString) {
        this.commandString = commandString;
    }

    public String getCommandString() {
        return commandString;
    }
}
