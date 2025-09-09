package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.HasId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class InMemoryRepository<T extends HasId> implements IRepository<T> {
    protected final Map<Long, T> data = new ConcurrentHashMap<>();

    private final AtomicLong sec = new AtomicLong(0);

    public List<T> findAll() { return new ArrayList<>(data.values()); }

    public Optional<T> findById(long id) { return Optional.ofNullable(data.get(id)); }
    public T create(T e) {
        long id = sec.incrementAndGet();
        e.setId(id);
        data.put(id, e);
        return e;
    }
    public T upsert(long id, T e) {
        long prevId = sec.longValue();
        if(prevId < id){
            sec.set(id);
        }
        e.setId(id);
        data.put(id, e);
        return e;
    }
    public boolean delete(long id) { return data.remove(id) != null; }
}
