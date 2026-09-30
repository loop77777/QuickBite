package com.quickbite.dto;


import com.quickbite.dao.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericRepositoryImpl<T> implements Repository<T> {
    private Map<Integer, T> database = new HashMap<>();

    @Override
    public void save(int id, T entity) {
        database.put(id, entity);
    }

    @Override
    public T findById(int id) {
        return database.get(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(database.values());
    }
}