package org.example.xss.services;

import org.springframework.stereotype.Service;

@Service
public class VulnerableService {
    
    public String formatMessage(String userInput) {
        return "<div style='color: blue;'>" + userInput + "</div>";
    }
}