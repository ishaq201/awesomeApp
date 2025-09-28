package org.example.ssrf.controllers;

import org.example.ssrf.services.HttpService;
import org.example.ssrf.services.UrlProcessor;
import org.example.ssrf.services.RequestSource;
import org.example.ssrf.services.RequestSink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ssrf")
public class SsrfController {

    @Autowired
    private HttpService httpService;
    
    @Autowired
    private UrlProcessor urlProcessor;
    
    @Autowired
    private RequestSource requestSource;
    
    @Autowired
    private RequestSink requestSink;

    @GetMapping("/direct")
    public String directSsrf(@RequestParam String url) throws IOException {
        // Vulnerable: Direct HTTP request to user-controlled URL
        return httpService.fetchContent(url);
    }

    @GetMapping("/multifile")
    public String multiFileSsrf(@RequestParam String endpoint) throws IOException {
        String processedUrl = urlProcessor.processUrl(endpoint);
        String fullUrl = urlProcessor.buildUrl(processedUrl);
        return httpService.makeRequest(fullUrl);
    }

    @GetMapping("/sourcesink")
    public String sourceSinkSsrf() throws IOException {
        // Source in different file
        String targetUrl = requestSource.getTargetUrl();
        // Sink in different file
        return requestSink.executeRequest(targetUrl);
    }
}