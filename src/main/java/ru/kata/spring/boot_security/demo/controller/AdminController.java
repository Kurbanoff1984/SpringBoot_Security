package ru.kata.spring.boot_security.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleServiceImp roleServiceImp;

    @Autowired
    public AdminController(UserService userService, RoleServiceImp roleServiceImp) {
        this.userService = userService;
        this.roleServiceImp = roleServiceImp;
    }

    @GetMapping
    public String showAllUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";

    }

    @GetMapping("/{id}")
    public String showOneUser(@PathVariable("id") long id1, ModelMap model) {
        model.addAttribute("user", userService.getUser(id1));
        return "user";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") @Valid User user) {
        return "new";

    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/new";


        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "/edit";

        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}