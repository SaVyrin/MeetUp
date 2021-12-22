package acquaintance;

import java.util.List;
import java.util.Objects;

public class Person {
    private final int id;
    private final String login;
    private final String password;
    private final String name;
    private final String surname;
    private final int age;
    private final String sex;
    private final String city;
    private final List<String> interests;
    private int points = 0;

    public Person(int id, String login, String password,
                  String name, String surname, int age, String sex,
                  String city, List<String> interests) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;
        this.city = city;
        this.interests = interests;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getCity() {
        return city;
    }

    public List<String> getInterests() {
        return interests;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
