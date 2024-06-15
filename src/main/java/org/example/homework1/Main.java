package org.example.homework1;


import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> personList = new ArrayList();
        Collections.addAll(personList,
                new Person("Person1", 24, 100000.0, new Department("IT")),
                new Person("Person2", 21, 80000.0, new Department("HR")),
                new Person("Person3", 42, 180000.0, new Department("IT")),
                new Person("Person4", 38, 280000.0, new Department("Management")),
                new Person("Person5", 35, 120000.0, new Department("HR")));
        System.out.println(findMostYoungestPerson(personList));
        System.out.println(findMostExpensiveDepartment(personList));
        System.out.println(groupByDepartment(personList));
        System.out.println(groupByDepartmentName(personList));
        System.out.println(getDepartmentOldestPerson(personList));
    }

    /**
     * Найти самого молодого сотрудника
     **/
    static Optional<Person> findMostYoungestPerson(List<Person> personList) {
        return personList.stream().min((p1, p2) -> {
            if (p1.getAge() > p2.getAge()) {
                return 1;
            }
             return -1;
        });
    }

    /**
     * Найти департамент, в котором работает сотрудник с самой большой зарплатой
     **/
    static Optional<Department> findMostExpensiveDepartment(List<Person> personList) {
        return personList.stream().sorted((p1, p2) -> {
            if (p1.getSalary() < p2.getSalary()) {
                return 1;
            }
            return -1;
        }).map(Person::getDepartment).findFirst();
    }

    /**
     * Сгруппировать сотрудников по департаментам
     **/
    static Map<Department, List<Person>> groupByDepartment(List<Person> personList) {
        return personList.stream().collect(Collectors.groupingBy(Person::getDepartment));
    }

    /**
     * Сгруппировать сотрудников по названиям департаментов
     **/
    static Map<String, List<Person>> groupByDepartmentName(List<Person> personList) {
        return personList.stream().collect(Collectors.groupingBy(
            p -> p.getDepartment().getName()));
    }

    /**
     * В каждом департаменте найти самого старшего сотрудника
     **/
    static Map<String, Person> getDepartmentOldestPerson(List<Person> personList) {
        return personList.stream().collect(Collectors.toMap(
                p -> p.getDepartment().getName(),
                p -> p,
                (p1,p2) -> {
                    if (p1.getAge() > p2.getAge()) {
                            return p1;
                    }
                    return p2;
                }
                ));
    }
}