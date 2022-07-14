package com.movie.controller;

import com.movie.dto.UserDTO;
import com.movie.facade.UserFacade;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Setter
public class UserController {

    UserFacade userFacade;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "/register")
    @ResponseBody
    public String register(@RequestBody UserDTO userDTO, BindingResult bindingResult, Model model) {
        boolean isUserRegistered = userFacade.existsUserByEmail(userDTO);
        if (bindingResult.hasErrors() || isUserRegistered) {
            return "index";
        } else {
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            userFacade.register(userDTO);
            return "index";
        }
    }
}
