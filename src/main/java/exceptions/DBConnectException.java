package exceptions;

public class DBConnectException extends ConnectException {
    public DBConnectException(String message) {
        super(message);
    }
}
