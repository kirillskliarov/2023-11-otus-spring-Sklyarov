package ru.otus.hw.repositories;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcBookRepository implements BookRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            var result = namedParameterJdbcOperations.queryForObject("""
                        SELECT
                            b.id "b_id",
                            b.title "b_title",
                            a.id "a_id",
                            a.full_name "a_full_name",
                            g.id "g_id",
                            g.name "g_name"
                        FROM books AS b
                            LEFT JOIN authors AS a ON a.id = b.author_id
                            LEFT JOIN genres AS g ON g.id = b.genre_id
                        WHERE b.id = :id
                    """, params, new BookRowMapper());
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return namedParameterJdbcOperations.query("""
                        SELECT
                            b.id "b_id",
                            b.title "b_title",
                            a.id "a_id",
                            a.full_name "a_full_name",
                            g.id "g_id",
                            g.name "g_name"
                        FROM books AS b
                            LEFT JOIN authors AS a
                            ON a.id = b.author_id
                            LEFT JOIN genres AS g
                            ON g.id = b.genre_id
                """, new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    private Book insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());
        var keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "insert into books (title, author_id, genre_id) values (:title, :authorId, :genreId)",
                params,
                keyHolder,
                new String[]{"id"}
        );

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        var bookId = book.getId();
        this.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", bookId);
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());

        namedParameterJdbcOperations.update("""
                            UPDATE books
                            SET title = :title,
                                author_id = :authorId,
                                genre_id = :genreId
                            WHERE id = :id
                """, params);

        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        private static Author getAuthor(ResultSet rs) throws SQLException {
            var id = rs.getLong("a_id");
            var fullName = rs.getString("a_full_name");

            return new Author(id, fullName);
        }

        private static Genre getGenre(ResultSet rs) throws SQLException {
            var id = rs.getLong("g_id");
            var name = rs.getString("g_name");

            return new Genre(id, name);
        }

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("b_id");
            var title = rs.getString("b_title");
            var author = getAuthor(rs);
            var genre = getGenre(rs);

            return new Book(id, title, author, genre);
        }
    }
}
