package connections;

import exceptions.ConnectException;

public interface Connection<E> {

    E connect() throws ConnectException;

}
