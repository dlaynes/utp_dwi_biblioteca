package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.HasId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class InMemoryRepository<T extends HasId> {
    protected final Map<Long, T> data = new ConcurrentHashMap<>();

    private final AtomicInteger sec = new AtomicInteger(0);

    public List<T> findAll() { return new ArrayList<>(data.values()); }

    public Optional<T> findById(long id) { return Optional.ofNullable(data.get(id)); }
    public T create(T e) {
        long id = sec.incrementAndGet();
        e.setId(id);
        data.put(id, e);
        return e;
    }
    public T upsert(long id, T e) {
        e.setId(id);
        data.put(id, e);
        return e;
    }
    public boolean delete(long id) { return data.remove(id) != null; }
}
