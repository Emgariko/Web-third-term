package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;
    
@Controller
public class UserPage extends Page{
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    private String getUserById(Model model, @PathVariable String id) {
        try {
            Long longId = Long.parseLong(id);
            User user = userService.findById(longId);
            model.addAttribute("curUser", user);
        } catch (NumberFormatException ignored) {
            return "redirect:/";
        }
        return "UserPage";
    }
}
