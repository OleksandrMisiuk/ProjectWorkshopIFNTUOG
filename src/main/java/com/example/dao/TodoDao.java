package com.example.dao;

import com.example.domain.Todo;

import javax.persistence.EntityManager;
import java.util.List;

public class TodoDao {

    private EntityManager em;

    public TodoDao(EntityManager em) {
        this.em = em;
    }

    public List<Todo> findAll(){
        em.clear();
        return em.createNativeQuery("SELECT  * from todos", Todo.class).getResultList();
    }

    public Todo create(Todo todo){
        em.getTransaction().begin();
        em.persist(todo);
        em.getTransaction().commit();
        return todo;
    }

    public Todo update(Todo todo){
        em.getTransaction().begin();
        todo = em.merge(todo);
        em.getTransaction().commit();
        return todo;
    }
}
