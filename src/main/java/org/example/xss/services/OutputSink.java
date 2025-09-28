package org.example.xss.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class OutputSink {
    
    public void writeToResponse(String content, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        // Sink: Writing user data directly to HTTP response
        out.println("<html><body><div>" + content + "</div></body></html>");
        out.close();
    }
}