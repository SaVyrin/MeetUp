package acquaintance;

import connections.Connection;
import connections.DBConnection;
import database.Database;

import java.util.List;

public class People {
    public static List<Person> generatePeopleList() {
        Connection<java.sql.Connection> connection = new DBConnection();
        java.sql.Connection sqlConnection = connection.connect();

        if (sqlConnection != null) {
            Database peopleDB = new Database(sqlConnection);
            return peopleDB.getPeopleFromDB();
        }
        return null;
    }
}
