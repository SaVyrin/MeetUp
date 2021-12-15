package acquaintance;

import database.Database;

import java.util.List;

public class People {
    public static List<Person> generatePeopleList() {
        Database peopleDB = new Database();
        return peopleDB.getPerson("", "");
    }
}
