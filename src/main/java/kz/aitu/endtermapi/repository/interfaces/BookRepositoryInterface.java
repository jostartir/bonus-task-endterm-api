package kz.aitu.endtermapi.repository.interfaces;

import kz.aitu.endtermapi.model.BookBase;

import java.sql.SQLException;

public interface BookRepositoryInterface extends CRUDRepository<BookBase, Integer> {
    BookBase getByIsbn(String isbn) throws SQLException;


}
