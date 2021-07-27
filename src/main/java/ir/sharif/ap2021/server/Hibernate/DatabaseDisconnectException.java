package ir.sharif.ap2021.server.Hibernate;

public class DatabaseDisconnectException extends Exception{
    public DatabaseDisconnectException(Throwable cause) {
        super(cause);
    }

    public DatabaseDisconnectException() {

    }
}
