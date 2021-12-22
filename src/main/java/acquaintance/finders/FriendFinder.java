package acquaintance.finders;

import acquaintance.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FriendFinder implements Finder {

    private final List<Person> people;
    private final Person person;

    public FriendFinder(List<Person> people, Person person) {
        this.people = people;
        this.person = person;
    }

    @Override
    public List<Person> find() {
        List<Person> probableFriends = new ArrayList<>();

        for (Person acquaintance : people) {

            if (person.equals(acquaintance)) {
                continue;
            }

            int personAge = person.getAge();
            int acqAge = acquaintance.getAge();
            if (!(personAge - 5 <= acqAge && personAge + 5 >= acqAge)) {
                acquaintance.setPoints(acquaintance.getPoints() + 1);
            }

            String personCity = person.getCity();
            String acqCity = acquaintance.getCity();
            if (!(personCity.equals(acqCity))) {
                acquaintance.setPoints(acquaintance.getPoints() + 1);
            }

            List<String> personInterests = person.getInterests();
            List<String> acqInterests = acquaintance.getInterests();
            for (String personInterest : personInterests) {
                if (acqInterests.contains(personInterest)) {
                    acquaintance.setPoints(acquaintance.getPoints() + 1);
                }
            }
            probableFriends.add(acquaintance);
        }

        Comparator<Person> friendsPointsComparator = Comparator.comparing(Person::getPoints);
        probableFriends.sort(friendsPointsComparator);

        return probableFriends;
    }
}
