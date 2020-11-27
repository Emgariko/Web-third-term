package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessagesServlet extends HttpServlet {
    List<Message> messages = new ArrayList<>();
    Gson gson = new Gson();
    private static class Message {
        private String user;
        private String text;
        public Message (String usr, String msg) {
            user = usr;
            text = msg;
        }

        @Override
        public String toString() {
            return user + ":" + text;
        }
    }

    private void printObjectJSONToResponse(Object object, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String json = gson.toJson(object);
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        switch (uri) {
            case "/message/auth": {
                response.setContentType("application/json");
                String userName = request.getParameter("user");
                if (userName == null) {
                    userName = (String) session.getAttribute("user");
                } else {
                    session.setAttribute("user", userName);
                }
                printObjectJSONToResponse(userName, response);
                break;
            }
            case "/message/findAll": {
                printObjectJSONToResponse(messages, response);
                break;
            }
            case "/message/add": {
                String userName = (String) session.getAttribute("user");
                String text = request.getParameter("text");
                messages.add(new Message(userName, text));
                break;
            }
        }
    }
}
