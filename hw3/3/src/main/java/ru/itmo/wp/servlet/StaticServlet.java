package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    private static final File STATIC_DIRECTORY = new File("/home/emil/work/itmo/web/hw/hw3/1/src/main/webapp/static/");

    private void writeFileToResponceOutputStream(File file, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(Files.readAllBytes(file.toPath()));
        outputStream.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] filesUri = uri.split("\\+");
        for (String s : filesUri) {
            File sourceDirectoryFile = new File(STATIC_DIRECTORY, s);
            File file = new File(getServletContext().getRealPath("/static/" + s));
            if (sourceDirectoryFile.isFile()) {
                writeFileToResponceOutputStream(sourceDirectoryFile, response);
            } else if (file.isFile()) {
                writeFileToResponceOutputStream(file, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
            }
        }
        if (response.getStatus() != HttpServletResponse.SC_NOT_FOUND) {
            response.setContentType(getContentTypeFromName(filesUri[0]));
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
