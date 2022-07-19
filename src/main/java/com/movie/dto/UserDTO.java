package com.movie.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;


}
