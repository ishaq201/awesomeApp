package org.example.protected;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/unreachable")
public class UnreachableVulnerabilities {

    @GetMapping("/protected-xss")
    public void protectedXss(@RequestParam String input, HttpServletResponse response) throws IOException {
        // NOT REACHABLE: Hardcoded condition that's never true
        if (1 == 2) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            // Vulnerable XSS code that's never executed
            out.println("<html><body><h1>" + input + "</h1></body></html>");
            out.close();
        }
        response.getWriter().println("Access denied");
    }

    @GetMapping("/protected-sqli")
    public String protectedSqli(@RequestParam String userId) throws Exception {
        // NOT REACHABLE: Environment variable that doesn't exist
        if ("production".equals(System.getenv("ENABLE_DANGEROUS_QUERIES"))) {
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
            Statement stmt = conn.createStatement();
            // Vulnerable SQL injection code that's never executed
            String query = "SELECT * FROM users WHERE id = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(query);
            return "Query executed: " + query;
        }
        return "Feature disabled";
    }

    @GetMapping("/protected-ssrf")
    public String protectedSsrf(@RequestParam String target) throws IOException {
        // NOT REACHABLE: Method that always returns false
        if (isDebugModeEnabled()) {
            // Vulnerable SSRF code that's never executed
            URL url = new URL(target);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            return "Debug request to " + target + " returned: " + responseCode;
        }
        return "Debug mode disabled";
    }
    
    private boolean isDebugModeEnabled() {
        // Always returns false - making SSRF unreachable
        return false;
    }
}