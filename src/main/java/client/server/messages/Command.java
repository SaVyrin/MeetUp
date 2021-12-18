package client.server.messages;

public enum Command {
    MESSAGE("MESSAGE"),
    SERVER("SERVER"),
    LON_IN("LOG_IN"),
    LOG_OUT("LOG_OUT");

    public static final String SEPARATOR = ":";

    private final String commandString;

    Command(String commandString) {
        this.commandString = commandString;
    }

    public String getCommandString() {
        return commandString;
    }
}
