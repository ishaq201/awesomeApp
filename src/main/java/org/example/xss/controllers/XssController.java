package org.example.xss.controllers;

import org.example.xss.services.UserInputProcessor;
import org.example.xss.services.HtmlGenerator;
import org.example.xss.services.ResponseBuilder;
import org.example.xss.services.InputSource;
import org.example.xss.services.OutputSink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/xss")
public class XssController {

    @Autowired
    private UserInputProcessor inputProcessor;
    
    @Autowired
    private HtmlGenerator htmlGenerator;
    
    @Autowired
    private ResponseBuilder responseBuilder;
    
    @Autowired
    private InputSource inputSource;
    
    @Autowired
    private OutputSink outputSink;

    @GetMapping(value = "/direct", produces = MediaType.TEXT_HTML_VALUE)
    public void directXss(@RequestParam String input, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h1>" + input + "</h1></body></html>");
        out.close();
    }

    @GetMapping(value = "/multifile", produces = MediaType.TEXT_HTML_VALUE)
    public void multiFileXss(@RequestParam String data, HttpServletResponse response) throws IOException {
        String processedInput = inputProcessor.processInput(data);
        String htmlContent = htmlGenerator.generateHtml(processedInput);
        String finalResponse = responseBuilder.buildResponse(htmlContent);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(finalResponse);
        out.close();
    }

    @GetMapping(value = "/sourcesink", produces = MediaType.TEXT_HTML_VALUE)
    public void sourceSinkXss(HttpServletResponse response) throws IOException {
        // Source in different file
        String userData = inputSource.getUserData();
        // Sink in different file
        outputSink.writeToResponse(userData, response);
    }
}