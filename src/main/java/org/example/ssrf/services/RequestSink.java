package org.example.ssrf.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class RequestSink {
    
    public String executeRequest(String targetUrl) throws IOException {
        // Sink: Making HTTP request to user-controlled URL
        URL url = new URL(targetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3000);
        
        try {
            int responseCode = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            return "SSRF executed to " + targetUrl + " - Response: " + response.toString().substring(0, Math.min(50, response.length()));
        } catch (Exception e) {
            return "SSRF attempted to " + targetUrl + " - Error: " + e.getMessage();
        }
    }
}