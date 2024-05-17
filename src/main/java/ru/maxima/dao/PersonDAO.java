package ru.maxima.dao;

import org.springframework.stereotype.Component;
import ru.maxima.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private Long PEOPLE_COUNT = 0L;
    private List<Person> people;


    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Сергей"));
        people.add(new Person(++PEOPLE_COUNT, "Павел"));
        people.add(new Person(++PEOPLE_COUNT, "Tagir"));
    }

    public List<Person> getAllPeople() {
        return people;
    }

    public Person findById(Long id) {
        return people.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(Person personFromView, Long id) {
        Person toBeUpdated = findById(id);
        toBeUpdated.setName(personFromView.getName());
    }

    public void delete(Long id) {
        people.removeIf(p -> p.getId().equals(id));
    }
}
