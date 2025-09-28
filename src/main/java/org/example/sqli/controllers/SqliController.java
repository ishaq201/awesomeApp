package org.example.sqli.controllers;

import org.example.sqli.services.UserService;
import org.example.sqli.services.QueryService;
import org.example.sqli.services.DataSource;
import org.example.sqli.services.QuerySink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/sqli")
public class SqliController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private QueryService queryService;
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private QuerySink querySink;

    @GetMapping("/direct")
    public String directSqli(@RequestParam String userId) throws SQLException {
        // Vulnerable: Direct SQL injection
        return userService.getUserByIdDirect("SELECT * FROM users WHERE id = '" + userId + "'");
    }

    @GetMapping("/multifile")
    public String multiFileSqli(@RequestParam String username) throws SQLException {
        String processedInput = queryService.processInput(username);
        String query = queryService.buildQuery(processedInput);
        return userService.executeQuery(query);
    }

    @GetMapping("/sourcesink")
    public String sourceSinkSqli() throws SQLException {
        // Source in different file
        String userInput = dataSource.getUserInput();
        // Sink in different file
        return querySink.executeUserQuery(userInput);
    }
}