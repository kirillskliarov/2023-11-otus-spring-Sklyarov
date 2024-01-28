package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> findCommentsByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);

        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        try {
            var result = em.find(Comment.class, id);
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }
}
