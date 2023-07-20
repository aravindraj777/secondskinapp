package com.secondskin.clothes.controller;

import com.secondskin.clothes.Dto.CreateUserRequest;
import com.secondskin.clothes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    UserService userService;



    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            if (error != null) {
                model.addAttribute("error`", "Invalid username or password");
            }
            return "login";
        }
        return "redirect:/";
    }


    @GetMapping("/signup")
    public String getSignUpPage(Model model) {
        model.addAttribute("signuprequest",new CreateUserRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("signuprequest") CreateUserRequest userRequest, Model model) {
        boolean result=userService.createUser(userRequest);
        if(!result && userService.getFlag()==1) {
            model.addAttribute("error", "Username or email already exists");
            return "/signup";
        }else if(!result && userService.getFlag()==0){
            return "/signup";

        }else {
            return "redirect:/login";
        }
    }
}
