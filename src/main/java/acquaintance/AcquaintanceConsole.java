package acquaintance;

import acquaintance.finders.CoupleFinder;
import acquaintance.finders.Finder;
import acquaintance.finders.FriendFinder;

import java.util.List;

public class AcquaintanceConsole {
    private final List<Person> people;
    private final Person newPerson;

    public AcquaintanceConsole(List<Person> people, Person newPerson) {
        this.people = people;
        this.newPerson = newPerson;
    }

    public Person acquaintance(String choice) {
        switch (choice) {
            case "couple" -> {
                Finder friendFinder = new FriendFinder(people, newPerson);
                List<Person> found = friendFinder.find();
                if (!found.isEmpty()) {
                    return found.get(0);
                }
            }
            case "friend" -> {
                Finder coupleFinder = new CoupleFinder(people, newPerson);
                List<Person> found = coupleFinder.find();
                if (!found.isEmpty()) {
                    return found.get(0);
                }
            }
        }
        return null;
    }
}
