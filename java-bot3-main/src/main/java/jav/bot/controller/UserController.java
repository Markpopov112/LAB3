package jav.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jav.bot.entity.User;
import jav.bot.service.UserService;

@Controller
@RequestMapping({"/users"})
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping({"/register"})
    public String registerUser(@ModelAttribute User user) {
        this.userService.registerNewUser(user);
        return "redirect:/login";
    }

    @GetMapping({"/register"})
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registerView";
    }
}

