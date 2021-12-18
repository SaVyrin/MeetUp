package acquaintance.finders;

import acquaintance.Person;
import acquaintance.finders.Finder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CoupleFinder implements Finder {
    List<Person> people;
    Person person;

    public CoupleFinder(List<Person> people, Person person) {
        this.people = people;
        this.person = person;
    }

    @Override
    public List<Person> find() {
        List<Person> probableCouples = new ArrayList<>();

        for (Person acquaintance : people) {

            if (person.equals(acquaintance)) {
                continue;
            }

            String personSex = person.getSex();
            String acqSex = acquaintance.getSex();
            if (personSex.equals(acqSex)) {
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
            probableCouples.add(acquaintance);
        }

        Comparator<Person> couplePointsComparator = Comparator.comparing(Person::getPoints);
        probableCouples.sort(couplePointsComparator);

        return probableCouples;
    }
}
