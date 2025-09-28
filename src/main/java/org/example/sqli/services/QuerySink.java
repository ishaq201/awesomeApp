package org.example.sqli.services;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class QuerySink {
    
    public String executeUserQuery(String userQuery) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement stmt = conn.createStatement();
        // Sink: Executing user-provided SQL query directly
        String fullQuery = "SELECT * FROM users WHERE name LIKE '%" + userQuery + "%'";
        ResultSet rs = stmt.executeQuery(fullQuery);
        return "Executed: " + fullQuery;
    }
}