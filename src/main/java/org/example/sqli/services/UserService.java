package org.example.sqli.services;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class UserService {
    
    public String getUserByIdDirect(String query) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement stmt = conn.createStatement();
        // Vulnerable: Executing user-controlled SQL directly
        ResultSet rs = stmt.executeQuery(query);
        return "Query executed: " + query;
    }
    
    public String executeQuery(String sqlQuery) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement stmt = conn.createStatement();
        // Vulnerable: Executing dynamically built query
        ResultSet rs = stmt.executeQuery(sqlQuery);
        return "Result: " + sqlQuery;
    }
}