package kz.aitu.endtermapi.controller;

import kz.aitu.endtermapi.dto.BookRequest;
import kz.aitu.endtermapi.model.*;
import kz.aitu.endtermapi.patterns.factory.BookFactory;
import kz.aitu.endtermapi.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET http://localhost:8080/books
    @GetMapping
    public List<BookBase> getAll() {
        return bookService.getAll();
    }

    // GET http://localhost:8080/books/5
    @GetMapping("/{id}")
    public BookBase getById(@PathVariable int id) {
        return bookService.getById(id);
    }

    @PostMapping
    public int create(@RequestBody BookRequest req) {
        return bookService.create(BookFactory.create(req));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody BookRequest req) {
        BookBase book = BookFactory.create(req);
        bookService.update(id,book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        bookService.delete(id);
    }

}
