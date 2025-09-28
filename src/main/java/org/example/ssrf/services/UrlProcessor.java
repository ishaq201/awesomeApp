package org.example.ssrf.services;

import org.springframework.stereotype.Service;

@Service
public class UrlProcessor {
    
    public String processUrl(String input) {
        // Pass through without validation
        return input;
    }
    
    public String buildUrl(String endpoint) {
        // Vulnerable: Building URL with unsanitized input - allows full URL override
        if (endpoint.startsWith("http")) {
            return endpoint; // Direct URL passthrough
        }
        return "http://api.internal.com/" + endpoint;
    }
}