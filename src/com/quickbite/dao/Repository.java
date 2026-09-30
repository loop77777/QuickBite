package com.quickbite.dao;


import java.util.List;

public interface Repository<T> {
    void save(int id, T entity);
    T findById(int id);
    List<T> findAll();
}