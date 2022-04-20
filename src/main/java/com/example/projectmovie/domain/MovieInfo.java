package com.example.projectmovie.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@Table(name = "movie_info")
public class MovieInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Description should not be empty!")
    private String description;
    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "enum('LONG', 'SHORT')")
    @NotNull(message = "Movie type should not be empty!")
    private MovieType movieType;
    @Min(value = 10, message = "Run time should be at least 10!")
    @Max(value = 400, message = "Run time can't be more than 400!")
    private Double length;

    @Lob
    private Byte[] image;

    @OneToOne
    @ToString.Exclude
    private Movie movie;

    public void removeMovie(Movie movie) {
        movie.setMovieInfo(null);
        this.movie = null;
    }
}
