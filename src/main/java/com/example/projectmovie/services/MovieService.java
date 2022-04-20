package com.example.projectmovie.services;

import com.example.projectmovie.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MovieService {

    List<Movie> findAll();

    Page<Movie> findAllPaginated(Pageable pageable);

    Movie findById(Long id);

    Movie save(Movie movie);

    void deleteById(Long id);

    Movie findByTitle(String title);

    List<Movie> findByGenre(String genreName);

    Page<Movie> findAllSortedPaginated(Pageable pageable);
}
