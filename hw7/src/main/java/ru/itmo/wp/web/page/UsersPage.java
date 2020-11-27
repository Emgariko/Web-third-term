package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            User newUser = userService.find(user.getId());
            request.getSession().setAttribute("user", newUser);
        }
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    private void setAdmin(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = (User) request.getSession().getAttribute("user");
        Long targetUserId = Long.valueOf(request.getParameter("userId"));
        String buttonValue = (String) request.getParameter("buttonValue");
        boolean boolValue = buttonValue.equals("enable");
        userService.validateSetAdmin(user, targetUserId);
        userService.setAdmin(targetUserId, boolValue);
        if (boolValue) {
            view.put("buttonValue", "disable");
        } else {
            view.put("buttonValue", "enable");
        }
    }
}
