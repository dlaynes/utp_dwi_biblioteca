package com.dwigs.biblioteca.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {

    public Optional<T> findById(long id);

    public List<T> findAll();

    public T create(T obj);

    public T upsert(long id, T obj);

    public boolean delete(long id);
}
