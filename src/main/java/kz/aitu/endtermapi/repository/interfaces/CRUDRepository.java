package kz.aitu.endtermapi.repository.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
public interface CRUDRepository<T, ID> {
    ID create(T entity) throws SQLException;
    Optional<T> getById(ID id) throws SQLException;
    List<T> getAll() throws SQLException;
    boolean update(ID id, T entity) throws SQLException;
    boolean delete(ID id) throws SQLException;
}
