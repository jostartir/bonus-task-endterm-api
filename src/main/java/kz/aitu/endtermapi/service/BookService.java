package kz.aitu.endtermapi.service;

import kz.aitu.endtermapi.exception.DatabaseOperationException;
import kz.aitu.endtermapi.exception.DuplicateResourceException;
import kz.aitu.endtermapi.exception.InvalidInputException;
import kz.aitu.endtermapi.exception.ResourceNotFoundException;
import kz.aitu.endtermapi.model.BookBase;
import kz.aitu.endtermapi.patterns.singleton.CacheManager;
import kz.aitu.endtermapi.repository.interfaces.AuthorRepositoryInterface;
import kz.aitu.endtermapi.repository.interfaces.BookRepositoryInterface;
import kz.aitu.endtermapi.service.interfaces.BookServiceInterface;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class BookService implements BookServiceInterface {

    private final BookRepositoryInterface bookRepo;
    private final AuthorRepositoryInterface authorRepo;
    private final String CACHE_KEY = "all_books";

    public BookService(BookRepositoryInterface bookRepo , AuthorRepositoryInterface authorRepo){
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    public int create(BookBase book) {
        validate(book);

        try {
            if (bookRepo.getByIsbn(book.getIsbn()) != null) {
                throw new DuplicateResourceException("Book with this ISBN already exists: " + book.getIsbn());
            }

            if (!authorRepo.getById(book.getAuthor().getAuthorId()).isPresent()) {
                throw new InvalidInputException("Author does not exist: id=" + book.getAuthor().getAuthorId());
            }

            int newId = bookRepo.create(book);

            CacheManager.getInstance().invalidate(CACHE_KEY);

            return newId;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create book", e);
        }
    }

    public List<BookBase> getAll() {

        Object cached = CacheManager.getInstance().get(CACHE_KEY);
        if (cached != null){
            System.out.println("Cache returning datd from memory");
            return (List<BookBase>) cached;
        }
        try {
            System.out.println("Fetching from database");
            List<BookBase> books = bookRepo.getAll();
            CacheManager.getInstance().put(CACHE_KEY, books);
            return books;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch books", e);
        }
    }

    public BookBase getById(int id) {
        try {
            return bookRepo.getById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + id));
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch book by id", e);
        }
    }

    public void update(int id, BookBase book) {
        validate(book);

        try {
            BookBase existing = bookRepo.getById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + id));



            if (!existing.getIsbn().equals(book.getIsbn()) && bookRepo.getByIsbn(book.getIsbn()) != null) {
                throw new DuplicateResourceException("Book with this ISBN already exists: " + book.getIsbn());
            }

            if (!authorRepo.getById(book.getAuthor().getAuthorId()).isPresent()) {
                throw new InvalidInputException("Author does not exist: id=" + book.getAuthor().getAuthorId());
            }

            boolean ok = bookRepo.update(id, book);
            if (!ok) {
                throw new ResourceNotFoundException("Book not found: id=" + id);
            }
            CacheManager.getInstance().invalidate(CACHE_KEY);

        }catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update book", e);
        }
    }

    public void delete(int id) {
        try {
            boolean ok = bookRepo.delete(id);
            if (!ok){
                throw new ResourceNotFoundException("Book not found: id=" + id);
            }
            CacheManager.getInstance().invalidate(CACHE_KEY);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete book", e);
        }
    }

    private void validate(BookBase b) {
        if (b == null) {
            throw new InvalidInputException("Book is null");
        }

        try {
            b.requireValid();
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException(ex.getMessage());
        }
    }
}
