package ru.job4j.rest.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.rest.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
