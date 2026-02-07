package kz.aitu.endtermapi.service.interfaces;

import kz.aitu.endtermapi.model.BookBase;

import java.util.List;

public interface BookServiceInterface {
    int create(BookBase book);
    List<BookBase> getAll();
    BookBase getById(int id);
    void update(int id, BookBase book);
    void delete(int id);
}
