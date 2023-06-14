package com.weatherknow.Controlller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ThymleafController {
        @GetMapping("/unsubscribe/{email}")
        public String showUnsubscribePage(@PathVariable("email") String email, Model model) {
            model.addAttribute("email", email);
            return "unsubscribe";
        }
}