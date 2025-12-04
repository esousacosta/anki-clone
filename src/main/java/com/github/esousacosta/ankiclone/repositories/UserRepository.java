package com.github.esousacosta.ankiclone.repositories;

import com.github.esousacosta.ankiclone.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public final User createUser(String firstName, String lastName, String userName) {
        int id = ID_GENERATOR.incrementAndGet();

        User newUser = new User(id, firstName, lastName, userName, null);

        userByIdMap.put(id, newUser);

        // persist user to the database
        // ...

        return newUser;
    }
}
