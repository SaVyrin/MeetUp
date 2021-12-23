package connections;

import exceptions.DBConnectException;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements Connection<java.sql.Connection> {

    private static final String connectionString = "jdbc:mysql://localhost:3306/test";
    private static final String dbLogin = "root";
    private static final String dbPassword = "root";

    @Override
    public java.sql.Connection connect() throws DBConnectException {
        try {
            return DriverManager.getConnection(connectionString, dbLogin, dbPassword);
        } catch (SQLException e) {
            throw new DBConnectException("Error connecting to DB");
        }
    }
}
