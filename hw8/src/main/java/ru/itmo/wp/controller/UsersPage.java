package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/setStatus")
    public String setStatus(@ModelAttribute("userForm") UserCredentials userForm, HttpSession httpSession) {
        System.out.println(1);
        return "UsersPage";
    }

    @GetMapping("/users/all")
    public String users(Model model, HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            putError(httpSession, "This page is only for authorized users");
            return "redirect:/";
        }
        model.addAttribute("userForm", new UserCredentials());
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }
}
