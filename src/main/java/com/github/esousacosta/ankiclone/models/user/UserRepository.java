package com.github.esousacosta.ankiclone.models.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    private Map<Integer, User> userByIdMap = new ConcurrentHashMap<>();

    public UserRepository() {
        userByIdMap.put(1, new User(1, "Emanoel", "Costa", "edesousacosta", null));
        userByIdMap.put(2, new User(2, "Julia", "Costa", "jcco", null));
    }

    public final User createUser(String firstName, String lastName, String userName) {
        int id = ID_GENERATOR.incrementAndGet();

        User newUser = new User(id, firstName, lastName, userName, null);

        userByIdMap.put(id, newUser);

        // persist user to the database
        // ...

        return newUser;
    }

    public final User findById(int id) {
        return userByIdMap.get(id);
    }

    public final List<User> findAll() {
        return userByIdMap.values().stream().toList();
    }
}
