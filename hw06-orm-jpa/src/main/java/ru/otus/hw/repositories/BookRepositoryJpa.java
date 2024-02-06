package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        var result = em.find(Book.class, id);
        return Optional.ofNullable(result);
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("""
                select b from Book b
                join fetch b.genre
                join fetch b.author
                """, Book.class);

        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }
}
