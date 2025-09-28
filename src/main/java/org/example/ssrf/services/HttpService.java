package org.example.ssrf.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpService {
    
    public String fetchContent(String urlString) throws IOException {
        // Vulnerable: Making HTTP request to user-controlled URL
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        return "Fetched from " + urlString + ": " + response.toString().substring(0, Math.min(100, response.length()));
    }
    
    public String makeRequest(String targetUrl) throws IOException {
        // Vulnerable: Making request to dynamically built URL
        URL url = new URL(targetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        
        int responseCode = connection.getResponseCode();
        return "Request to " + targetUrl + " returned: " + responseCode;
    }
}