package com.grcp.jpa.playground.relationship.simple.repository;

import com.grcp.jpa.playground.relationship.simple.model.Author;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public class AuthorRepository {

    private final EntityManager entityManager;

    public AuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Author> findById(Integer id) {
        Author author = entityManager.find(Author.class, id);
        return Optional.ofNullable(author);
    }

    public List<Author> findAll() {
        return entityManager.createQuery("from Author").getResultList();
    }

    public Optional<Author> findByName(String name) {
        Author author = entityManager.createNamedQuery("Author.findByName", Author.class)
                .setParameter("name", name)
                .getSingleResult();
        return Optional.ofNullable(author);
    }

    public Optional<Author> save(Author author) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(author);
            entityManager.getTransaction().commit();
            return Optional.of(author);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void delete(Author author) {
        entityManager.getTransaction().begin();
        entityManager.remove(author);
        entityManager.getTransaction().commit();
    }
}
