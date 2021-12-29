package client.server;

import acquaintance.People;
import acquaintance.Person;
import acquaintance.finders.CoupleFinder;
import acquaintance.finders.Finder;
import acquaintance.finders.FriendFinder;

import java.util.ArrayList;
import java.util.List;

public class PersonToShowChooser {

    private final List<Person> people = People.generatePeopleList();

    private List<Person> peopleToShow = new ArrayList<>();
    private int currShownPersonIndex = 0;

    private final Person loggedInPerson;
    private Person currShownPerson;

    public PersonToShowChooser(Person loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
    }

    public void leftChanger() {
        if (currShownPersonIndex == 0) {
            nextPersonToShow();
            return;
        }
        currShownPersonIndex--;
        nextPersonToShow();
    }

    public void rightChanger() {
        if (currShownPersonIndex == peopleToShow.size() - 1) {
            nextPersonToShow();
            return;
        }
        currShownPersonIndex++;
        nextPersonToShow();
    }

    public void chooseFriends() {
        findPeople(new FriendFinder(people, loggedInPerson));
        currShownPersonIndex = 0;
        nextPersonToShow();
    }

    public void chooseCouples() {
        findPeople(new CoupleFinder(people, loggedInPerson));
        currShownPersonIndex = 0;
        nextPersonToShow();
    }

    private void findPeople(Finder finder) {
        peopleToShow = finder.find();
    }

    private void nextPersonToShow() {
        currShownPerson = peopleToShow.get(currShownPersonIndex);
    }

    public Person getCurrShownPerson() {
        return currShownPerson;
    }
}
