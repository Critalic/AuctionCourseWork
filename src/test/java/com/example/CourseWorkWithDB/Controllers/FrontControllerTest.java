package com.example.CourseWorkWithDB.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.example.CourseWorkWithDB.Model.JPA.Customer;
import java.util.Optional;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.List;

class FrontControllerTest {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("cleverCloud");

    @Test
    void testDBConnectionSelect() {
        EntityManager em = factory.createEntityManager();

        Query q = em.createQuery("select c from Customer c");
        List<Customer> customerEntities = q.getResultList();

        System.out.println(customerEntities);
    }

    @Test
    void testPersist() {
        Customer customer = new Customer("admin4", "admin4@gmail.com", "123");

        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        System.out.println(em.contains(customer) + "---");
        em.persist(customer);
        System.out.println(em.contains(customer) + "---");
        em.getTransaction().commit();
        em.close();

        em = factory.createEntityManager();
        System.out.println(em.contains(customer) + "---");
        System.out.println(customer);
    }

    @Test
    void testRemove() {
        EntityManager em = factory.createEntityManager();

        Query q = em.createQuery("select c from Customer c");

        em.getTransaction().begin();

        List<Customer> customerEntities = q.getResultList();
        em.remove(customerEntities.get(customerEntities.size() - 1));
        em.getTransaction().commit();

        customerEntities = q.getResultList();
        System.out.println(customerEntities);
    }

    @Test
    void testFind() {
        EntityManager em = factory.createEntityManager();

        Optional<Customer> c =  Optional.of(em.find(Customer.class, 1L));

        System.out.println(c);

//        NativeQuery query = em.createNativeQuery();
    }
}