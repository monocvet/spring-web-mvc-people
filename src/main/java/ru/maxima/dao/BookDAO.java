package ru.maxima.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maxima.models.Book;
import ru.maxima.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("select * from book", new BookMapper());
    }

    public Book findById(Long id) {
        return jdbcTemplate.queryForObject("select * from book where id = ?",
                new Object[]{id}, new BookMapper());
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(title, author, year) values (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(Book book, Long id) {
        jdbcTemplate.update("update book set title = ?, author = ?, year = ? where id = ? ",
                book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from book where id = ?", id);
    }


    public Optional<Person> getBookOwner(Long id) {
        return jdbcTemplate.query("select person. * from book join person on book.person_id = Person.id where book.id = ?",
                new Object[]{id}, new PersonMapper()).stream().findAny();
    }


    public void release(Long id) {
        jdbcTemplate.update("update book set person_id = null where id = ?", id);
    }

    public void assign(Long bookId, Person person) {
        jdbcTemplate.update("update book set person_id = ? where id = ?", person.getId(), bookId);
    }

}
