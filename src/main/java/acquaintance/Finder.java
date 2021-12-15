package acquaintance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Finder {
    public static List<Person> findCouple(List<Person> people, Person person) {
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

    public static List<Person> findFriend(List<Person> people, Person person) {
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
