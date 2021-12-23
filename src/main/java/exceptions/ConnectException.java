package exceptions;

public abstract class ConnectException extends Exception {
    public ConnectException(String message) {
        super(message);
    }
}
