package com.github.esousacosta.ankiclone.models.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public interface UserRepository extends JpaRepository<User, Integer> {
    static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    public final User createUser(String firstName, String lastName, String userName) {
        int id = ID_GENERATOR.incrementAndGet();

        User newUser = new User(id, firstName, lastName, userName, null);

        userByIdMap.put(id, newUser);

        // persist user to the database
        // ...

        return newUser;
    }
}
