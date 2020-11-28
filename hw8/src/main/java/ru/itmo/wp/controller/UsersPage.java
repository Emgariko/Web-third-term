package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.form.UserStatusChangeForm;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UsersPage extends Page {
    private final String ENABLE_ACTION_NAME = "enable";
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/setStatus")
    public String setStatus(@ModelAttribute("userStatusChangeForm") UserStatusChangeForm userStatusChangeForm, HttpSession httpSession) {
        if (!getUser(httpSession).getStatus()) {
            putError(httpSession, "Your account was disabled");
            return "redirect:/users/all";
        }
        Long userId = userStatusChangeForm.getUserId();
        String action = userStatusChangeForm.getAction();
        if (userId.equals(getUser(httpSession).getId()) && !action.equals(ENABLE_ACTION_NAME)) {
            putError(httpSession, "You can not disable your own account");
            return "redirect:/users/all";
        }
        userService.updateStatus(userId, action.equals(ENABLE_ACTION_NAME));
        return "redirect:/users/all";
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
