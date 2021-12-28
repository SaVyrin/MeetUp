package database;

import acquaintance.Person;
import exceptions.DBConnectException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PeopleDatabase {

    private static final String connectionString = "jdbc:mysql://localhost:3306/test";
    private static final String dbLogin = "root";
    private static final String dbPassword = "root";

    public PeopleDatabase() {
    }

    public List<Person> getPeopleFromDB() throws DBConnectException {
        String query = "SELECT * FROM meetup_test";

        try (Connection connection = DriverManager.getConnection(connectionString, dbLogin, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet queryResult = preparedStatement.executeQuery();

            List<Person> personList = new ArrayList<>();
            while (queryResult.next()) {
                int id = Integer.parseInt(queryResult.getString(1));
                String login = queryResult.getString(2);
                String password = queryResult.getString(3);
                String name = queryResult.getString(4);
                String surname = queryResult.getString(5);
                int age = Integer.parseInt(queryResult.getString(6));
                String sex = queryResult.getString(7);
                String city = queryResult.getString(8);
                String interestsString = queryResult.getString(9);
                List<String> interests = new ArrayList<>(Arrays.asList(interestsString.split(",")));
                personList.add(new Person(id, login, password, name, surname, age, sex, city, interests));
            }

            return personList;

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new DBConnectException("Error connecting to DB");
        }
    }

    public Person getPersonFromDB(String personLogin, String personPassword) throws DBConnectException {
        String query = "SELECT * FROM meetup_test WHERE login = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(connectionString, dbLogin, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, personLogin);
            preparedStatement.setString(2, personPassword);

            ResultSet queryResult = preparedStatement.executeQuery();

            List<Person> personList = new ArrayList<>();
            while (queryResult.next()) {
                int id = Integer.parseInt(queryResult.getString(1));
                String login = queryResult.getString(2);
                String password = queryResult.getString(3);
                String name = queryResult.getString(4);
                String surname = queryResult.getString(5);
                int age = Integer.parseInt(queryResult.getString(6));
                String sex = queryResult.getString(7);
                String city = queryResult.getString(8);
                String interestsString = queryResult.getString(9);
                List<String> interests = new ArrayList<>(Arrays.asList(interestsString.split(",")));

                personList.add(new Person(id, login, password, name, surname, age, sex, city, interests));
            }

            return personList.get(0);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new DBConnectException("Error connecting to DB");
        }
    }

    public void insertPerson(Person person) throws DBConnectException {

        String insertPerson = "INSERT INTO meetup_test\n" +
                "(login, password, name, surname, age, sex, city, interests)\n" +
                "VALUES\n" +
                "(?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(connectionString, dbLogin, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(insertPerson)) {

            preparedStatement.setString(1, person.getLogin());
            preparedStatement.setString(2, person.getPassword());
            preparedStatement.setString(3, person.getName());
            preparedStatement.setString(4, person.getSurname());
            preparedStatement.setInt(5, person.getAge());
            preparedStatement.setString(6, person.getSex());
            preparedStatement.setString(7, person.getCity());
            preparedStatement.setString(8, getPersonInterestsAsString(person));

            preparedStatement.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new DBConnectException("Error connecting to DB");
        }
    }

    private String getPersonInterestsAsString(Person person) {
        StringBuilder personInterestsAsString = new StringBuilder();

        int count = 0;
        List<String> interests = person.getInterests();
        for (String interest : interests) {
            personInterestsAsString.append(interest);
            if (count != interests.size() - 1) {
                personInterestsAsString.append(",");
            }
            count++;
        }

        return personInterestsAsString.toString();
    }
}
