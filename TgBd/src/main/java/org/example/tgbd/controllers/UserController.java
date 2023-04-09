package org.example.tgbd.controllers;

import org.example.tgbd.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/{id}/{name}")
    public Boolean getOne(@PathVariable("id") Long id, @PathVariable("name") String name) {
        return userService.createNew(id, name);
    }

}
