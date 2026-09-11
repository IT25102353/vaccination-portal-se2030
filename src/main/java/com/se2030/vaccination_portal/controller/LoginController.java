package com.se2030.vaccination_portal.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private static final String MOCK_USERNAME = "admin";
    private static final String MOCK_PASSWORD = "admin123";

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                         HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (MOCK_USERNAME.equals(username) && MOCK_PASSWORD.equals(password)) {
            request.getSession(true).setAttribute("loggedIn", true);
            request.getSession().setAttribute("username", username);
            return "redirect:/dashboard";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password.");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
