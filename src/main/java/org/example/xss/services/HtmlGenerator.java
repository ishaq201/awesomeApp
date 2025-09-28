package org.example.xss.services;

import org.springframework.stereotype.Service;

@Service
public class HtmlGenerator {
    
    public String generateHtml(String content) {
        return "<div class='content'>" + content + "</div>";
    }
}