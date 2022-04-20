package com.example.projectmovie.repositories;

import com.example.projectmovie.domain.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
    List<Movie> findAll(Sort sort);
}
