package database;

import exceptions.DBConnectException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendsDatabase {

    private static final String connectionString = "jdbc:mysql://localhost:3306/test";
    private static final String dbLogin = "root";
    private static final String dbPassword = "root";

    public FriendsDatabase() {
    }

    public List<List<String>> getAllFriendsFromDB() throws DBConnectException {
        String query = "SELECT * FROM meetup_friends";

        try (Connection connection = DriverManager.getConnection(connectionString, dbLogin, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet queryResult = preparedStatement.executeQuery();

            List<List<String>> friendsList = new ArrayList<>();
            while (queryResult.next()) {
                String firstPersonLogin = queryResult.getString(2);
                String secondPersonLogin = queryResult.getString(3);

                List<String> friends = new ArrayList<>();
                friends.add(firstPersonLogin);
                friends.add(secondPersonLogin);

                friendsList.add(friends);
            }

            return friendsList;

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new DBConnectException("Error connecting to DB");
        }
    }

    public void insertTwoFriends(String firstPersonLogin, String secondPersonLogin) throws DBConnectException {
        String insertPerson = "INSERT INTO meetup_friends\n" +
                "(first_person_login, second_person_login)\n" +
                "VALUES\n" +
                "(?, ?);";

        try (Connection connection = DriverManager.getConnection(connectionString, dbLogin, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(insertPerson)) {

            preparedStatement.setString(1, firstPersonLogin);
            preparedStatement.setString(2, secondPersonLogin);

            preparedStatement.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new DBConnectException("Error connecting to DB");
        }
    }
}
