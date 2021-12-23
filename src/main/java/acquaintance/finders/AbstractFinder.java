package acquaintance.finders;

import acquaintance.Person;

import java.util.List;

public abstract class AbstractFinder implements Finder {

    public final List<Person> people;
    public final Person person;

    public AbstractFinder(List<Person> people, Person person) {
        this.people = people;
        this.person = person;
    }

    @Override
    public List<Person> find() {
        return null;
    }
}
