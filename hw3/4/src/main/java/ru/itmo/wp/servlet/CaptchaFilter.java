package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

public class CaptchaFilter extends HttpFilter {
    public static final String PASSED_CAPTCHA_VALUE = "passed";
    private static final String PASSED_CAPTCHA_PAGE = "<!doctype html>\n" +
            "\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "  <meta charset=\"utf-8\">\n" +
            "\n" +
            "  <title>Coderofces</title>\n" +
            "  <meta name=\"description\" content=\"The HTML5 Herald\">\n" +
            "  <meta name=\"author\" content=\"SitePoint\">\n" +
            "\n" +
            "\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "Captcha passed." +
            "</body>\n" +
            "</html>";

    private String generateHtml(byte[] imageBytes) {
        return "<!doctype html>\n" +
                "\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "\n" +
                "  <title>Coderofces</title>\n" +
                "  <meta name=\"description\" content=\"The HTML5 Herald\">\n" +
                "  <meta name=\"author\" content=\"SitePoint\">\n" +
                "\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/normalize.css\">"
                + "\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../webapp/static/css/style.css\">" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <script src=\"js/scripts.js\"></script>\n" +
                "<div class=\"captcha-block\" style=\"text-align: center\">" +
                "<img src=\"data:image/png;base64, " + Base64.getEncoder().encodeToString(imageBytes) + "\"/>" +
                "<p>Please type captcha</p>" +
                "<form method=\"POST\">\n" +
                "    <input type=\"text\" name=\"captcha\" id=\"captcha\">\n" +
                "</form>\n" +
                "</div>" + "\n" +
                "</body>\n" +
                "</html>";
    }

    private void printCaptcha(HttpServletResponse response, byte[] imageBytes) throws IOException {
        response.setContentType("text/html");
        response.getWriter().print(generateHtml(imageBytes));
        response.getWriter().flush();
    }

    private void sendCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int randomNum = ThreadLocalRandom.current().nextInt(100, 999 + 1);
        byte[] imageBytes = ImageUtils.toPng(Integer.toString(randomNum));
        request.getSession().setAttribute("expected-captcha-value", Integer.toString(randomNum));
        printCaptcha(response, imageBytes);
    }

    private void resendCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int num = Integer.parseInt((String) request.getSession().getAttribute("expected-captcha-value"));
        byte[] imageBytes = ImageUtils.toPng(Integer.toString(num));
        request.getSession().setAttribute("expected-captcha-value", Integer.toString(num));
        printCaptcha(response, imageBytes);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("GET") || request.getMethod().equals("POST")) {
            String expectedCaptchaValue = (String) request.getSession().getAttribute("expected-captcha-value");
            if (expectedCaptchaValue == null) {
                sendCaptcha(request, response);
            } else if (expectedCaptchaValue.equals(PASSED_CAPTCHA_VALUE)) {
                super.doFilter(request, response, chain);
            } else {
                String userAnswer = request.getParameter("captcha");
                if (userAnswer == null) {
                    resendCaptcha(request, response);
                } else if (userAnswer.equals(expectedCaptchaValue)) {
                    request.getSession().setAttribute("expected-captcha-value", PASSED_CAPTCHA_VALUE);
                    response.sendRedirect(request.getRequestURI());
                } else {
                    sendCaptcha(request, response);
                }
            }
        }
    }
}
