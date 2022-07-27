package com.movie.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MovieDTO {

    private Long id;

    private String name;

    private String description;

    private String picture;

    private Double rating;

    private Integer year;

    private Long minutes;

}
