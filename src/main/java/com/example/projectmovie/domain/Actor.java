package com.example.projectmovie.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "First name should not be empty!")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name should not be empty!")
    private String lastName;

    @OneToOne(mappedBy = "actor",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private ContactInfo contactInfo;

    @ManyToMany(mappedBy = "actors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private List<Movie> movies;


    public void removeMovie(Movie movie) {
            movie.getActors().remove(this);
            movies.remove(movie);
    }
}
