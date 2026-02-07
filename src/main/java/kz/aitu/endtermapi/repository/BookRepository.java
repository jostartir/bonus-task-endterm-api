package kz.aitu.endtermapi.repository;

import kz.aitu.endtermapi.patterns.singleton.DBManager;
import kz.aitu.endtermapi.model.*;
import kz.aitu.endtermapi.repository.interfaces.BookRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository implements BookRepositoryInterface {

    public Integer create(BookBase book) throws SQLException {
        String sql = "INSERT INTO books(title, isbn, price, author_id, book_type) VALUES(?, ?, ?, ?, ?)";

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getName());
            ps.setString(2, book.getIsbn());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getAuthor().getAuthorId());
            ps.setString(5, book.getBookType().name());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }

                throw new SQLException("No generated key returned for books insert");
            }
        }
    }

    public List<BookBase> getAll() throws SQLException {
        String sql = "SELECT b.book_id, b.title, b.isbn, b.price, b.book_type, " +
                        "       a.author_id, a.full_name, a.birth_year " +
                        "FROM books b " +
                        "JOIN authors a ON a.author_id = b.author_id " +
                        "ORDER BY b.book_id";

        List<BookBase> list = new ArrayList<>();

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapBook(rs));
            }
        }
        return list;
    }

    @Override
    public Optional<BookBase> getById(Integer bookId) throws SQLException {
        String sql = "SELECT b.book_id, b.title, b.isbn, b.price, b.book_type, " +
                        "       a.author_id, a.full_name, a.birth_year " +
                        "FROM books b " +
                        "JOIN authors a ON a.author_id = b.author_id " +
                        "WHERE b.book_id = ?";

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, bookId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    return Optional.of(mapBook(rs));
                }
                return Optional.empty();
            }
        }
    }

    public boolean update(Integer bookId, BookBase book) throws SQLException {
        String sql = "UPDATE books SET title = ?, isbn = ?, price = ?, author_id = ?, book_type = ? " +
                        "WHERE book_id = ?";

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, book.getName());
            ps.setString(2, book.getIsbn());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getAuthor().getAuthorId());
            ps.setString(5, book.getBookType().name());
            ps.setInt(6, bookId);

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    public boolean delete(Integer bookId) throws SQLException {
        String sql = "DELETE FROM books WHERE book_id = ?";

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    public BookBase getByIsbn(String isbn) throws SQLException {
        String sql = "SELECT b.book_id, b.title, b.isbn, b.price, b.book_type, " +
                        "       a.author_id, a.full_name, a.birth_year " +
                        "FROM books b " +
                        "JOIN authors a ON a.author_id = b.author_id " +
                        "WHERE b.isbn = ?";

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    return mapBook(rs);
                }
                return null;
            }
        }
    }


    private BookBase mapBook(ResultSet rs) throws SQLException {
        int bookId = rs.getInt("book_id");
        String title = rs.getString("title");
        String isbn = rs.getString("isbn");
        double price = rs.getDouble("price");

        BookType type = BookType.fromDb(rs.getString("book_type"));

        int authorId = rs.getInt("author_id");
        String fullName = rs.getString("full_name");

        Integer birthYear = (Integer) rs.getObject("birth_year"); // проще чем rs.wasNull()

        Author author = new Author(authorId, fullName, birthYear);

        if (type == BookType.EBOOK) {
            return new EBook(bookId, title, isbn, price, author);
        } else {
            return new PrintedBook(bookId, title, isbn, price, author);
        }
    }
}
