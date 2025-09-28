package org.example.controllers;

import org.example.xss.services.VulnerableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private VulnerableService vulnerableService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/echo")
    public String echo(@RequestParam String userInput, Model model) {
        String processedMessage = vulnerableService.formatMessage(userInput);
        model.addAttribute("message", processedMessage);
        return "result";
    }
}