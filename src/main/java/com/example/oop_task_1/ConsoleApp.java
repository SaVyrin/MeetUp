package com.example.oop_task_1;

import acquaintance.AcquaintanceConsole;
import acquaintance.People;
import acquaintance.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите свои данные: Имя, Фамилия, возраст, пол, город, интересы");

            String name = scanner.next();
            String surname = scanner.next();
            int age = scanner.nextInt();
            String sex = scanner.next();
            String city = scanner.next();
            String interest1 = scanner.next();
            String interest2 = scanner.next();

            List<String> interests = new ArrayList<>();
            interests.add(interest1);
            interests.add(interest2);

            Person newPerson = new Person(0, "login", "password", name, surname, age, sex, city, interests);

            List<Person> people = People.generatePeopleList();

            System.out.println("Хотите познакомиться(1) или найти друга(2)?");

            AcquaintanceConsole acq = new AcquaintanceConsole(people, newPerson);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    Person acquaintance = acq.acquaintance("couple");
                    if (acquaintance != null) {
                        System.out.println("Вы познакомились с " + acquaintance.getName());
                    } else {
                        System.out.println("Подходящей пары не нашлось");
                    }
                }
                case 2 -> {
                    Person acquaintance = acq.acquaintance("friend");
                    if (acquaintance != null) {
                        System.out.println("Вы познакомились с " + acquaintance.getName());
                    } else {
                        System.out.println("Подходящего друга не нашлось");
                    }
                }
            }

            System.out.println("Завершить - 1, нет - любая цифра");
            int again = scanner.nextInt();
            if (again == 1) {
                break;
            }
        }
    }
}
