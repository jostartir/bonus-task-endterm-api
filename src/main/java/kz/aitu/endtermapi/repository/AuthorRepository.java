package kz.aitu.endtermapi.repository;

import kz.aitu.endtermapi.patterns.singleton.DBManager;
import kz.aitu.endtermapi.model.Author;
import kz.aitu.endtermapi.patterns.singleton.DBManager;
import kz.aitu.endtermapi.repository.interfaces.AuthorRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements AuthorRepositoryInterface {


    public Integer create(Author author) throws SQLException {
        String sql = "INSERT INTO authors(full_name, birth_year) VALUES(?, ?)";

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, author.getFullName());

            if (author.getBirthYear() == null){
                ps.setNull(2, Types.INTEGER);
            }else{
                ps.setInt(2, author.getBirthYear());
            }

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()){
                    return keys.getInt(1);
                }
                throw new SQLException("No generated key returned for authors insert");
            }
        }
    }


    public List<Author> getAll() throws SQLException {
        String sql = "SELECT author_id, full_name, birth_year FROM authors ORDER BY author_id";
        List<Author> list = new ArrayList<>();

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapAuthor(rs));
            }
        }
        return list;
    }
    @Override
    public Optional<Author> getById(Integer authorId) throws SQLException {
        String sql = "SELECT author_id, full_name, birth_year FROM authors WHERE author_id = ?";

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, authorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    return Optional.of(mapAuthor(rs));
                }
                return Optional.empty();
            }
        }
    }


    public boolean update(Integer authorId, Author author) throws SQLException {
        String sql = "UPDATE authors SET full_name = ?, birth_year = ? WHERE author_id = ?";

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, author.getFullName());

            if (author.getBirthYear() == null){
                ps.setNull(2, Types.INTEGER);
            }else{
                ps.setInt(2, author.getBirthYear());
            }

            ps.setInt(3, authorId);

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }


    public boolean delete(Integer authorId) throws SQLException {
        String sql = "DELETE FROM authors WHERE author_id = ?";

        try (Connection c = DBManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, authorId);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }


    private Author mapAuthor(ResultSet rs) throws SQLException {
        int id = rs.getInt("author_id");
        String name = rs.getString("full_name");

        int by = rs.getInt("birth_year");
        Integer birthYear;

        if (rs.wasNull()) {
            birthYear = null;
        } else {
            birthYear = by;
        }

        return new Author(id, name, birthYear);
    }
}
