package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.EventType;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class EnterPage extends Page {
    /*private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }*/

    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        userService.validateEnter(loginOrEmail, password);
        User user = null;
        if (userService.isLogin(loginOrEmail)) {
            user = userService.findByLoginAndPassword(loginOrEmail, password);
        } else {
            user = userService.findByEmailAndPassword(loginOrEmail, password);
        }
        setUser(user);

        Event event = new Event(null, user.getId(), EventType.ENTER, null);
        eventService.save(event);

        setMessage("Hello, " + user.getLogin());

        throw new RedirectException("/index");
    }
}
