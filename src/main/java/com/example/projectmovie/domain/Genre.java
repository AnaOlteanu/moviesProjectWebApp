package com.example.projectmovie.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Length(min = 5, max = 15)
    public String name;

    @ManyToMany(mappedBy = "genres",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Movie> movies;

    public Genre(Long genreId, String name) {
        this.id = genreId;
        this.name = name;
    }
}
