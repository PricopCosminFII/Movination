package com.movie.controller;

import com.movie.dto.UserDTO;
import com.movie.facade.UserFacade;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.*;


@Controller
@Setter
public class UserController {

    UserFacade userFacade;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {
        String error = userFacade.validateUser(userDTO);
        if (bindingResult.hasErrors() || error != null) {
            model.addAttribute("error", error);
        } else {
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            userFacade.register(userDTO);
            return "redirect:/";
        }
        return "register";
    }

    @GetMapping(value = "/register")
    public String showRegister() {
        return "register";
    }

    @GetMapping(path = "/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        return "login";
    }
}
