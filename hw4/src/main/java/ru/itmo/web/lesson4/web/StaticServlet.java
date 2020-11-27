package ru.itmo.web.lesson4.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    private static final File STATIC_DIRECTORY = new File("/home/emil/work/itmo/web/hw/hw4/src/main/webapp/");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        File sourceDirectoryFile = new File(STATIC_DIRECTORY, uri);
        if (sourceDirectoryFile.isFile()) {
            response.setContentType(getServletContext().getMimeType(sourceDirectoryFile.getCanonicalPath()));
            Files.copy(sourceDirectoryFile.toPath(), response.getOutputStream());
        } else {
            File file = new File(getServletContext().getRealPath(uri));
            if (file.isFile()) {
//                response.setContentType(request.getContentType());
                response.setContentType(getServletContext().getMimeType(file.getCanonicalPath()));
                OutputStream outputStream = response.getOutputStream();
                Files.copy(file.toPath(), outputStream);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
