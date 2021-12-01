package ru.job4j.rest.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private int inn;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar hiringDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "employees_persons",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = { @JoinColumn(name = "person_id")}
    )
    private Set<Person> persons = new HashSet<>();

    public static Employee of(String name, String surname, int inn) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setInn(inn);
        employee.setHiringDate(Calendar.getInstance());
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }

    public Calendar getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Calendar hiringDate) {
        this.hiringDate = hiringDate;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
