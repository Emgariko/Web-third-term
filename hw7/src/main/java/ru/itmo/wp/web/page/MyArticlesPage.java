package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "This page is only for logged users");
            request.getSession().setAttribute("messageType", "error");
            throw new RedirectException("/index");
        }
        view.put("userArticles", articleService.findAllUserArticles(user));
    }

    @Json
    private void setVisibility(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = (User) request.getSession().getAttribute("user");
        String buttonValue = (String) request.getParameter("buttonValue");
        Long articleId = Long.valueOf(request.getParameter("id"));
        boolean boolValue = buttonValue.equals("Hide");
        articleService.validateSetVisibility(user, articleId);
        articleService.setVisibility(articleId, boolValue);
        if (buttonValue.equals("Hide")) {
            view.put("buttonValue", "Show");
        } else {
            view.put("buttonValue", "Hide");
        }
    }

}
