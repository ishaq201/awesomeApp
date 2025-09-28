package org.example.xss.services;

import org.springframework.stereotype.Component;

@Component
public class UserInputProcessor {
    
    public String processInput(String rawInput) {
        return rawInput;
    }
}