# AwesomeApp - Vulnerable Web Application for Security Testing

A comprehensive Spring Boot application designed to demonstrate various security vulnerabilities and SAST tool capabilities, specifically built for testing Snyk Code analysis and reachability assessment.

## 🚨 **WARNING: FOR EDUCATIONAL PURPOSES ONLY**

This application contains **intentional security vulnerabilities**. Never deploy to production or expose to the internet.

## 📋 **Overview**

AwesomeApp demonstrates three major vulnerability categories across different architectural patterns:
- **Cross-Site Scripting (XSS)** - 9 vulnerabilities
- **SQL Injection** - 3 vulnerabilities  
- **Server-Side Request Forgery (SSRF)** - 3 vulnerabilities

Each vulnerability type includes examples of:
- Direct vulnerabilities (single file)
- Multi-file vulnerabilities (cross-service data flow)
- Source-sink vulnerabilities (cross-file source and sink separation)

## 🏗️ **Architecture**

```
src/main/java/org/example/
├── Main.java                    # Spring Boot application entry point
├── controllers/
│   └── MainController.java      # Main MVC controller
├── xss/
│   ├── controllers/
│   │   └── XssController.java   # XSS vulnerability endpoints
│   └── services/
│       ├── InputSource.java     # XSS source service
│       ├── OutputSink.java      # XSS sink service
│       ├── HtmlGenerator.java   # HTML generation service
│       ├── ResponseBuilder.java # Response building service
│       ├── UserInputProcessor.java # Input processing service
│       └── VulnerableService.java # Vulnerable formatting service
├── sqli/
│   ├── controllers/
│   │   └── SqliController.java  # SQL injection endpoints
│   └── services/
│       ├── DataSource.java      # SQL source service
│       ├── QuerySink.java       # SQL sink service
│       ├── QueryService.java    # Query building service
│       └── UserService.java     # Database service
├── ssrf/
│   ├── controllers/
│   │   └── SsrfController.java  # SSRF vulnerability endpoints
│   └── services/
│       ├── RequestSource.java   # SSRF source service
│       ├── RequestSink.java     # SSRF sink service
│       ├── HttpService.java     # HTTP request service
│       └── UrlProcessor.java    # URL processing service
└── protected/
    ├── ProtectedController.java     # Authentication-protected vulnerabilities
    └── UnreachableVulnerabilities.java # Dead code vulnerabilities
```

## 🚀 **Quick Start**

### Prerequisites
- Java 17+
- Gradle 7+

### Running the Application
```bash
# Clone and navigate to project
cd awesomeApp

# Run the application
gradle bootRun

# Access the application
open http://localhost:8080
```

## 🎯 **Vulnerability Endpoints**

### XSS Vulnerabilities
- `GET /xss/direct?input=<payload>` - Direct XSS
- `GET /xss/multifile?data=<payload>` - Multi-file XSS
- `GET /xss/sourcesink?payload=<payload>` - Source-sink XSS

### SQL Injection Vulnerabilities
- `GET /sqli/direct?userId=<payload>` - Direct SQLi
- `GET /sqli/multifile?username=<payload>` - Multi-file SQLi
- `GET /sqli/sourcesink?query=<payload>` - Source-sink SQLi

### SSRF Vulnerabilities
- `GET /ssrf/direct?url=<payload>` - Direct SSRF
- `GET /ssrf/multifile?endpoint=<payload>` - Multi-file SSRF
- `GET /ssrf/sourcesink?target=<payload>` - Source-sink SSRF

### Protected/Unreachable Vulnerabilities
- `GET /protected/admin-xss` - Requires admin authentication
- `GET /protected/internal-sqli` - IP address restricted
- `GET /protected/conditional-ssrf` - Debug mode only
- `GET /unreachable/*` - Dead code paths

## 🧪 **Test Payloads**

### XSS Payloads
```html
<script>alert('XSS')</script>
<img src=x onerror=alert('XSS')>
javascript:alert('XSS')
```

### SQL Injection Payloads
```sql
' OR '1'='1
' UNION SELECT * FROM users --
'; DROP TABLE users; --
```

### SSRF Payloads
```
http://169.254.169.254/latest/meta-data/
http://localhost:8080/admin
file:///etc/passwd
```

## 🔍 **SAST Analysis with Snyk**

### Running Snyk Code Scan
```bash
# Install Snyk CLI
npm install -g snyk

# Authenticate
snyk auth

# Trust the project directory
snyk trust /path/to/awesomeApp

# Run code analysis
snyk code test
```

### Expected Results
- **18 Total Issues**: 15 high-severity vulnerabilities + 3 low-severity hardcoded credentials
- **100% Detection Rate**: All reachable vulnerabilities detected
- **Cross-file Analysis**: Snyk traces data flow across multiple files and services
- **No Reachability Analysis**: Protected/unreachable vulnerabilities not distinguished

## 📊 **Reachability Analysis**

### Directly Reachable (15 vulnerabilities)
- All `/xss/*`, `/sqli/*`, `/ssrf/*` endpoints
- No authentication or authorization barriers
- Exploitable with single HTTP requests

### Not Directly Reachable (6+ vulnerabilities)
- `/protected/*` endpoints - Authentication/IP/configuration barriers
- `/unreachable/*` endpoints - Dead code paths
- **Note**: SAST tools don't detect reachability barriers

### Key Insights
- **SAST Limitation**: Cannot determine runtime reachability
- **False Positives**: Reports vulnerabilities in protected/dead code
- **Complementary Testing**: Requires DAST/IAST for accurate risk assessment

## 🛡️ **Security Learning Objectives**

### SAST Tool Capabilities
- ✅ Pattern-based vulnerability detection
- ✅ Cross-file data flow analysis
- ✅ Inter-procedural analysis
- ✅ Dependency injection awareness

### SAST Tool Limitations
- ❌ Runtime reachability analysis
- ❌ Authentication/authorization context
- ❌ Network topology awareness
- ❌ Configuration-dependent conditions

### Best Practices Demonstrated
- **Defense in Depth**: Multiple security layers needed
- **Input Validation**: Sanitize all user input
- **Output Encoding**: Escape data in output context
- **Parameterized Queries**: Prevent SQL injection
- **URL Validation**: Restrict SSRF attack vectors

## 🔧 **Remediation Examples**

### XSS Prevention
```java
// Vulnerable
out.println("<h1>" + userInput + "</h1>");

// Fixed
String safeInput = StringEscapeUtils.escapeHtml4(userInput);
out.println("<h1>" + safeInput + "</h1>");
```

### SQL Injection Prevention
```java
// Vulnerable
String query = "SELECT * FROM users WHERE id = '" + userId + "'";

// Fixed
String query = "SELECT * FROM users WHERE id = ?";
PreparedStatement stmt = conn.prepareStatement(query);
stmt.setString(1, userId);
```

### SSRF Prevention
```java
// Vulnerable
URL url = new URL(userUrl);

// Fixed
if (!isAllowedUrl(userUrl)) {
    throw new SecurityException("URL not allowed");
}
URL url = new URL(userUrl);
```

## 📚 **Educational Use Cases**

- **Security Training**: Hands-on vulnerability demonstration
- **SAST Tool Evaluation**: Compare detection capabilities
- **Reachability Research**: Study SAST limitations
- **Secure Coding Practice**: Learn remediation techniques
- **Penetration Testing**: Practice exploitation techniques

## ⚠️ **Security Warnings**

- **Never deploy to production**
- **Use only in isolated environments**
- **Contains intentional vulnerabilities**
- **For educational purposes only**
- **Keep application local/internal**

## 🤝 **Contributing**

This project is designed for security education. Contributions should:
- Add new vulnerability patterns
- Improve educational documentation
- Enhance SAST tool testing capabilities
- Maintain clear separation between reachable/unreachable code

## 📄 **License**

Educational use only. Not for production deployment.

---

**Remember**: This application demonstrates what NOT to do in production code. Use it to learn secure coding practices and understand how security tools work.