package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Collections;
import java.util.Map;

public class TalksPage extends Page {

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser() == null) {
            setMessage("Talks page is only for logged users");
            throw new RedirectException("/index");
        }
        view.put("users", userService.findAll());
        view.put("talks", talkService.findAllUserTalks(getUser()));
    }

    void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Talk talk = new Talk(null, getUser().getId(), Long.valueOf(request.getParameter("recipient")), request.getParameter("talkMessage"), null);
        view.put("users", userService.findAll());
        view.put("talks", talkService.findAllUserTalks(getUser()));
        talkService.validateSendMessage(talk);
        talkService.save(talk);
        throw new RedirectException("/talks");
    }
}
