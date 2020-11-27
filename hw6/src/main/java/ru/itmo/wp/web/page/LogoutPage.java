package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.EventType;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page{

    public void action(HttpServletRequest request, Map<String, Object> view) {
        Event event = new Event(null, getUser().getId(), EventType.LOGOUT, null);
        eventService.save(event);

        request.getSession().removeAttribute("user");

        setMessage("Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}
