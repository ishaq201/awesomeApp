package org.example.xss.services;

import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {
    
    public String buildResponse(String htmlContent) {
        return "<html><head><title>Multi-file XSS</title></head><body>" + htmlContent + "</body></html>";
    }
}