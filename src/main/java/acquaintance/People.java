package acquaintance;

import database.PeopleDatabase;
import exceptions.ConnectException;

import java.util.List;

public class People {
    public static List<Person> generatePeopleList() {
        try {
            PeopleDatabase peopleDB = new PeopleDatabase();
            return peopleDB.getPeopleFromDB();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
        return null;
    }
}
