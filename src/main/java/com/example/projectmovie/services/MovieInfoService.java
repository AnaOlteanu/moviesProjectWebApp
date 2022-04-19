package com.example.projectmovie.services;

import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.domain.MovieInfo;

public interface MovieInfoService {

    MovieInfo save(MovieInfo movieInfo);

    void deleteMovieInfo(Long id);

    MovieInfo findById(Long id);
}
