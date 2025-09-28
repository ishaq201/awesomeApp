package org.example.protected;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
@RequestMapping("/protected")
public class ProtectedController {

    @GetMapping("/admin-xss")
    public void protectedXss(@RequestParam String message, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // NOT REACHABLE: Requires admin authentication
        String userRole = (String) request.getSession().getAttribute("role");
        if (!"admin".equals(userRole)) {
            response.setStatus(403);
            return;
        }
        
        // Vulnerable XSS code that's protected by authentication
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h1>Admin Panel: " + message + "</h1></body></html>");
        out.close();
    }

    @GetMapping("/internal-sqli")
    public String protectedSqli(@RequestParam String userId, HttpServletRequest request) throws Exception {
        // NOT REACHABLE: Only accessible from internal network
        String clientIp = request.getRemoteAddr();
        if (!clientIp.startsWith("192.168.") && !clientIp.equals("127.0.0.1")) {
            return "Access denied - Internal only";
        }
        
        // Vulnerable SQL injection code that's protected by IP restriction
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM sensitive_data WHERE user_id = '" + userId + "'";
        ResultSet rs = stmt.executeQuery(query);
        return "Internal query executed: " + query;
    }

    @GetMapping("/conditional-ssrf")
    public String protectedSsrf(@RequestParam String target, @RequestParam String debugMode) throws IOException {
        // NOT REACHABLE: Only works in debug mode which is disabled in production
        if (!"true".equals(System.getProperty("debug.enabled"))) {
            return "Debug mode disabled";
        }
        
        // Vulnerable SSRF code that's only reachable when debug mode is enabled
        URL url = new URL(target);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        return "Debug request to " + target + " returned: " + responseCode;
    }
}