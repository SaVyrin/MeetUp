package fxml.controllers.scene.controller;

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

    public PersonToShowChooser(Person loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
    }

    public Person leftChanger() {
        if (currShownPersonIndex == 0) {
            return nextPersonToShow();
        }
        currShownPersonIndex--;
        return nextPersonToShow();
    }

    public Person rightChanger() {
        if (currShownPersonIndex == peopleToShow.size() - 1) {
            return nextPersonToShow();
        }
        currShownPersonIndex++;
        return nextPersonToShow();
    }

    public Person chooseFriends() {
        findPeople(new FriendFinder(people, loggedInPerson));
        currShownPersonIndex = 0;
        return nextPersonToShow();
    }

    public Person chooseCouples() {
        findPeople(new CoupleFinder(people, loggedInPerson));
        currShownPersonIndex = 0;
        return nextPersonToShow();
    }

    private void findPeople(Finder finder) {
        peopleToShow = finder.find();
    }

    private Person nextPersonToShow() {
        return peopleToShow.get(currShownPersonIndex);
    }
}
