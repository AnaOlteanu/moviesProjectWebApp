package com.example.projectmovie.services;

import com.example.projectmovie.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    Genre findById(Long id);

    Genre save(Genre genre);
}
