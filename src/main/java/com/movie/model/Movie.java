package com.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private Integer year;
    @Column
    private Long minutes;
    @Column
    private String description;
    @Column
    private String picture;
    @Column
    private Double rating;

    @ManyToMany(mappedBy = "movies", cascade = CascadeType.ALL)
    @UniqueElements
    private List<Category> categories;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Item> items;

    // to do something with category code - so we don't use the PK


}
