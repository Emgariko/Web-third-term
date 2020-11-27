package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage {
    ArticleService articleService = new ArticleService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
        view.put("articles", articleService.findAllArticles());
    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        String messageType = (String) request.getSession().getAttribute("messageType");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            view.put("messageType", messageType);
            request.getSession().removeAttribute("message");
            request.getSession().removeAttribute("messageType");
        }
    }
}
