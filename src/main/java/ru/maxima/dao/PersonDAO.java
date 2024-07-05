package ru.maxima.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.maxima.models.Book;
import ru.maxima.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPeople() {
        return jdbcTemplate.query("select * from person", new PersonMapper());
    }

    public Person findById(Long id) {
        return jdbcTemplate.queryForObject("select * from person where id = ?",
                new Object[]{id}, new PersonMapper());
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(name, age) values (?, ?)",
                person.getName(), person.getAge());
    }

    public void update(Person person, Long id) {
        jdbcTemplate.update("update person set name = ?, age = ? where id = ? ",
                person.getName(), person.getAge(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from person where id = ?", id);
    }

    public List<Book> showBooksByPerson(Long id) {
       return jdbcTemplate.query("select * from book where person_id = ?",
               new Object[]{id}, new BookMapper());
    }
}
