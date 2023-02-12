package com.sportyshoe.admin.user.controller;

import com.sportyshoe.admin.user.UserService;
import com.sportyshoe.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String listAll(Model model){
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users/users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        User user = new User();
        user.setEnabled(true);

        model.addAttribute("user", user);
        return "users/user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user){
        System.out.println(user);
        return "redirect/users/users";
    }

}
