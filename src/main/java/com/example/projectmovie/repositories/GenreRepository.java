package com.example.projectmovie.repositories;

import com.example.projectmovie.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("select g from Genre g where g.name = ?1")
    Optional<Genre> findGenreByName(String genreName);
}
