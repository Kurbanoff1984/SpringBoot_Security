package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void addUser(User user);

    User getUser(long id);

    void removeUserById(long id);


    void updateUser(long id, User user);


    boolean existsByUsername(String username);                        ///
}


