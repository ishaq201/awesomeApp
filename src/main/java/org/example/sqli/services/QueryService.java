package org.example.sqli.services;

import org.springframework.stereotype.Service;

@Service
public class QueryService {
    
    public String processInput(String input) {
        // Pass through without sanitization
        return input;
    }
    
    public String buildQuery(String username) {
        // Vulnerable: Building SQL with unsanitized input
        return "SELECT * FROM users WHERE username = '" + username + "'";
    }
}