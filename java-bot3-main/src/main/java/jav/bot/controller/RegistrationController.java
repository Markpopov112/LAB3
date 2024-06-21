package jav.bot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    public RegistrationController() {
    }

    @GetMapping({"/register"})
    public String showRegistrationForm() {
        logger.info("Accessing /register endpoint");
        return "registerView";
    }
}