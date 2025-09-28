package org.example.redirect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * CVE-2024-38816 - Spring Framework Open Redirect Vulnerability
 */
@Controller
public class RedirectController {

    @GetMapping("/redirect/direct")
    public RedirectView directRedirect(@RequestParam String url) {
        return new RedirectView(url);
    }

    @GetMapping("/redirect/login")
    public RedirectView loginRedirect(@RequestParam String returnUrl) {
        return new RedirectView(returnUrl);
    }

    @GetMapping("/redirect/logout")
    public RedirectView logoutRedirect(@RequestParam(defaultValue = "/") String next) {
        return new RedirectView(next);
    }
}