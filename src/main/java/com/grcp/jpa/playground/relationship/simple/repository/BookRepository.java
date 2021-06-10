package com.grcp.jpa.playground.relationship.simple.repository;

import com.grcp.jpa.playground.relationship.simple.model.Book;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public class BookRepository {

    private final EntityManager entityManager;

    public BookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Book> findById(Integer id) {
        Book book = entityManager.find(Book.class, id);
        return Optional.ofNullable(book);
    }

    public List<Book> findAll() {
        return entityManager.createQuery("from Book").getResultList();
    }

    public Optional<Book> findByName(String name) {
        Book book = entityManager.createQuery("SELECT b FROM Book b WHERE b.name = :name", Book.class)
                .setParameter("name", name)
                .getSingleResult();
        return Optional.ofNullable(book);
    }

    public Optional<Book> findByNameNamedQuery(String name) {
        Book book = entityManager.createNamedQuery("Book.findByName", Book.class)
                .setParameter("name", name)
                .getSingleResult();
        return Optional.ofNullable(book);
    }

    public Optional<Book> save(Book book) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(book);
            entityManager.getTransaction().commit();

            return Optional.of(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
