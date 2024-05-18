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
        people.add(new Person(++PEOPLE_COUNT, "Сергей",43));
        people.add(new Person(++PEOPLE_COUNT, "Павел",35));
        people.add(new Person(++PEOPLE_COUNT, "Тагир",26));
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
        toBeUpdated.setAge(personFromView.getAge());
    }

    public void delete(Long id) {
        people.removeIf(p -> p.getId().equals(id));
    }
}
