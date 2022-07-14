package com.example.CourseWorkWithDB.DAO.JPA;

import com.example.CourseWorkWithDB.Entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.CourseWorkWithDB.DAO.JPA.Utils.*;

public class CustomerDAO implements DAO<Customer> {

    private final EntityManagerFactory entityManagerFactory;

    public CustomerDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<Customer> get(Long identifier) {
        return Optional.of(entityManagerFactory.createEntityManager().find(Customer.class, identifier));
    }

    @Override
    public List<Customer> getAll(Customer identifier) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> customerRoot = criteriaQuery.from(Customer.class);

        Field[] fields = Arrays.stream(identifier.getClass().getDeclaredFields())
                .filter(field -> Objects.nonNull(getValue(field, identifier)))
                .filter(field -> !field.getType().equals(List.class) || field.getName().equals("passwordHash"))
                .toArray(Field[]::new);

        List<Predicate> searchCriteria = Arrays.stream(fields)
                .map(field -> convertToCriteriaPredicate(field, criteriaBuilder, customerRoot, identifier))
                .collect(Collectors.toList());

        criteriaQuery.select(customerRoot).where(criteriaBuilder.and(searchCriteria.toArray(new Predicate[0])));
        return manager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Customer> getAll() {
        EntityManager manager = entityManagerFactory.createEntityManager();
        TypedQuery<Customer> query = manager.createQuery("select c from Customer c", Customer.class);
        return query.getResultList();
    }

    @Override
    public void save(Customer object) {
        conductInTransaction(entityManager -> entityManager.persist(object), entityManagerFactory.createEntityManager());
    }

    @Override
    public void delete(Customer object) {
        conductInTransaction(entityManager -> entityManager.remove(entityManager.merge(object)),
                entityManagerFactory.createEntityManager());
    }

    @Override
    public void update(Customer object) {
        conductInTransaction(entityManager -> entityManager.merge(object), entityManagerFactory.createEntityManager());
    }
}
