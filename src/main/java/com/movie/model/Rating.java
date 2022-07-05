package com.movie.model;

import javax.persistence.*;

@Entity
@Table(name="rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer mark;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
    private Movie movie;
}
