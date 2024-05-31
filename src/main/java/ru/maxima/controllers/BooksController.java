package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.dao.BookDAO;
import ru.maxima.dao.PersonDAO;
import ru.maxima.models.Book;
import ru.maxima.models.Person;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showAllBooks(Model model) {
        final List<Book> allBooks = bookDAO.getAllBooks();
        model.addAttribute("allBooks", allBooks);
        return "books/view-with-all-books";
    }

    @GetMapping("/{id}")
    public String showBookById(@PathVariable("id") Long id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("bookById", bookDAO.findById(id));

        if (bookDAO.getBookOwner(id).isPresent()) {
            model.addAttribute("findOwnerById", bookDAO.getBookOwner(id).get());
        } else {
            model.addAttribute("allPerson", personDAO.getAllPeople());
        }
        return "books/view-with-book-by-id";
    }

    @GetMapping("/create")
    public String getPageToCreateNewBook(Model model) {
        model.addAttribute("newBook", new Book());
        return "books/view-to-create-new-book";
    }

    @PostMapping
    public String createNewBook(@ModelAttribute("newBook") @Valid Book book,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/view-to-create-new-book";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String getPageToEditBook(Model model, @PathVariable("id") Long id) {
        model.addAttribute("editedBook", bookDAO.findById(id));
        return "books/view-to-edit-book";
    }

    @PostMapping("/{id}")
    public String editBook(@PathVariable("id") Long id,
                           @ModelAttribute("editedBook") @Valid Book book,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/view-to-edit-book";
        }
        bookDAO.update(book, id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/select")
    public String addBookInPerson(@PathVariable("id") Long id, @ModelAttribute("person") Person person) {
        bookDAO.assign(id, person);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/free")
    public String freeBook(@PathVariable("id") Long id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }
}
