package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AddUser implements CommandLineRunner {
    private RoleServiceImp roleServiceImp;
    private UserServiceImpl userServiceImp;

    @Autowired
    public AddUser(RoleServiceImp roleServiceImp, UserServiceImpl userServiceImp) {
        this.roleServiceImp = roleServiceImp;
        this.userServiceImp = userServiceImp;
    }

    @Override
    public void run(String... args) {
        Role userTest = new Role(1L, "ROLE_USER");
        Role adminTest = new Role(2L, "ROLE_ADMIN");
        roleServiceImp.addRole(userTest);
        roleServiceImp.addRole(adminTest);
        List<Role> userSet = Stream.of(userTest).collect(Collectors.toList());
        List<Role> adminSet = Stream.of(adminTest, userTest).collect(Collectors.toList());

        User user = new User("user", "userLastname", 30, "user", userSet);
        User admin = new User("admin", "admin", 20, "admin", adminSet);
        userServiceImp.addUser(user);
        userServiceImp.addUser(admin);
    }
}
