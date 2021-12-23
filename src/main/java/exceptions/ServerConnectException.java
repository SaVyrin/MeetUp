package exceptions;

public class ServerConnectException extends ConnectException {
    public ServerConnectException(String message) {
        super(message);
    }
}
