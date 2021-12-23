package acquaintance;

import connections.Connection;
import connections.DBConnection;
import database.Database;
import exceptions.ConnectException;

import java.util.List;

public class People {
    public static List<Person> generatePeopleList() {
        Connection<java.sql.Connection> connection = new DBConnection();
        try {
            java.sql.Connection sqlConnection = connection.connect();
            Database peopleDB = new Database(sqlConnection);
            return peopleDB.getPeopleFromDB();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
        return null;
    }
}
