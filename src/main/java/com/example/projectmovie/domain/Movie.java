package com.example.projectmovie.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Title should not be empty!")
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "release_date")
    @NotNull(message = "Release date should not be empty!")
    private LocalDate releaseDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> genres;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"))
    private List<Actor> actors;

    @OneToOne(mappedBy = "movie",
    cascade = CascadeType.ALL, orphanRemoval = true)
    private MovieInfo movieInfo;


    public void removeGenre(Genre genre) {
        genre.getMovies().remove(this);
        genres.remove(genre);
    }

    public void removeActor(Actor actor) {
        actor.getMovies().remove(this);
        actors.remove(actor);
    }

}
