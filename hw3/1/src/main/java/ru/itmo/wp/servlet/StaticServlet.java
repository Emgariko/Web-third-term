package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticServlet extends HttpServlet {
    private static final File STATIC_DIRECTORY = new File("/home/emil/work/itmo/web/hw/hw3/1/src/main/webapp/static/");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        File sourceDirectoryFile = new File(STATIC_DIRECTORY, request.getRequestURI());
        if (sourceDirectoryFile.isFile()) {
            response.setContentType(getContentTypeFromName(uri));
            OutputStream outputStream = response.getOutputStream();
            Files.copy(sourceDirectoryFile.toPath(), outputStream);
            outputStream.flush();
        } else {
            File file = new File(getServletContext().getRealPath("/static" + uri));
            if (file.isFile()) {
                response.setContentType(getContentTypeFromName(uri));
                OutputStream outputStream = response.getOutputStream();
                Files.copy(file.toPath(), outputStream);
                outputStream.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
