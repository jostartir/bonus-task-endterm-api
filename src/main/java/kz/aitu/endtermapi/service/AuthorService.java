package kz.aitu.endtermapi.service;

import kz.aitu.endtermapi.exception.DatabaseOperationException;
import kz.aitu.endtermapi.exception.InvalidInputException;
import kz.aitu.endtermapi.exception.ResourceNotFoundException;
import kz.aitu.endtermapi.model.Author;
import kz.aitu.endtermapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class AuthorService {

    private final AuthorRepository repo = new AuthorRepository();

    public int create(Author author) {
        validate(author);
        try {
            return repo.create(author);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create author", e);
        }
    }

    public List<Author> getAll() {
        try {
            return repo.getAll();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch authors", e);
        }
    }

    public Author getById(int id) {
        try {
            return repo.getById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found: id=" + id));
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch author by id", e);
        }
    }

    public void update(int id, Author author) {
        validate(author);
        try {
            boolean ok = repo.update(id, author);
            if (!ok){
                throw new ResourceNotFoundException("Author not found: id=" + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update author", e);
        }
    }

    public void delete(int id) {
        try {
            boolean ok = repo.delete(id);
            if (!ok) {
                throw new ResourceNotFoundException("Author not found: id=" + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete author", e);
        }
    }

    private void validate(Author a) {
        if (a == null){
            throw new InvalidInputException("Author is null");
        }
        if (a.getFullName() == null || a.getFullName().trim().isEmpty()) {
            throw new InvalidInputException("Author full_name must not be empty");
        }
        if (a.getBirthYear() != null && (a.getBirthYear() < 0 || a.getBirthYear() > 2100)) {
            throw new InvalidInputException("Author birth_year is invalid");
        }
    }
}
