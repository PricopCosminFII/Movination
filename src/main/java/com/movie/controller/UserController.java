package com.movie.controller;

import com.movie.constants.MessageConstants;
import com.movie.dto.UserDTO;
import com.movie.facade.UserFacade;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


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
            model.addAttribute("firstname", userDTO.getFirstName());
            model.addAttribute("lastname", userDTO.getLastName());
            model.addAttribute("email", userDTO.getEmail());
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
    public String login(@RequestParam(value = "error", required = false) String error, HttpServletRequest request, Model model) {
        if (error != null) {
            model.addAttribute("error", MessageConstants.INVALID_USERNAME_OR_PASSWORD);
            setAttributeFromCookies(request, "email", "email", model);
        }
        return "login";
    }

    public void setAttributeFromCookies(HttpServletRequest request, String parameter, String attribute, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                setAttributesValueWithCookiesValue(cookie, parameter, attribute, model);
            }
        }
    }

    public void setAttributesValueWithCookiesValue(Cookie cookie, String parameter, String attribute, Model model) {
        if (cookie.getName().equals(parameter)) {
            model.addAttribute(attribute, cookie.getValue());
        }
    }

}
