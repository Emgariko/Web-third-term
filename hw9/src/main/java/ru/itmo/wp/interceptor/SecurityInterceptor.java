package ru.itmo.wp.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.itmo.wp.controller.IndexPage;
import ru.itmo.wp.controller.Page;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    private IndexPage indexPage;

    @Autowired
    public void setIndexPage(IndexPage indexPage) {
        this.indexPage = indexPage;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (Page.class.isAssignableFrom(method.getDeclaringClass())) {
                if (method.getAnnotation(Guest.class) != null) {
                    return true;
                }

                User user = indexPage.getUser(request.getSession());
                if (user != null) {
                    AnyRole anyRole = method.getAnnotation(AnyRole.class);
                    if (anyRole == null) {
                        return true;
                    }
                    for (Role.Name name : anyRole.value()) {
                        for (Role role : user.getRoles()) {
                            if (role.getName().equals(name)) {
                                return true;
                            }
                        }
                    }
                }

                indexPage.putError(request.getSession(), "Access is denied");
                response.sendRedirect(user != null ? "/accessDenied" : "/enter");
                return false;
            }
        }

        return true;
    }
}
