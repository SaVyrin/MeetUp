package acquaintance;

import java.util.ArrayList;
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
            case "couple": {
                 List<Person> found = Finder.findCouple(people, newPerson);
                 if (!found.isEmpty()){
                     return found.get(0);
                 }
                break;
            }
            case "friend": {
                List<Person> found = Finder.findFriend(people, newPerson);
                if (!found.isEmpty()){
                    return found.get(0);
                }
                break;
            }
        }
        return null;
    }
}
