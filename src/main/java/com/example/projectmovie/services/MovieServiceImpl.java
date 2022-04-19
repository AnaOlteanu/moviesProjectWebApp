package com.example.projectmovie.services;

import com.example.projectmovie.controllers.MovieController;
import com.example.projectmovie.domain.*;
import com.example.projectmovie.exception.NotFoundException;
import com.example.projectmovie.repositories.GenreRepository;
import com.example.projectmovie.repositories.MovieInfoRepository;
import com.example.projectmovie.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;
    GenreRepository genreRepository;


    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository){
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new LinkedList<>();
        movieRepository.findAll().iterator().forEachRemaining(movies::add);
        return movies;
    }

    @Override
    public Page<Movie> findAllPaginated(Pageable pageable) {
        List<Movie> movies = findAll();
        int currentPage = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int startItem = currentPage * pageSize;
        List<Movie> movieList;

        if(movies.size() < startItem){
            movieList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, movies.size());
            movieList = movies.subList(startItem, toIndex);
        }
        Page<Movie> moviePage = new PageImpl<>(movieList, PageRequest.of(currentPage, pageSize), movies.size());
        return moviePage;
    }


    @Override
    public Movie findById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if(movieOptional.isPresent()){
            return movieOptional.get();
        } else {
            throw new NotFoundException("Movie with id " + id + " not found");
        }
    }

    @Override
    public Movie save(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return savedMovie;
    }

    @Override
    public void deleteById(Long id) {
         Optional<Movie> movieOptional = movieRepository.findById(id);
         if(!movieOptional.isPresent()){
             throw new NotFoundException("Movie with id " + id + " not found");
         }

         Movie movie = movieOptional.get();
         List<Genre> genres = new LinkedList<>();
         movie.getGenres().iterator().forEachRemaining(genres::add);

         for(Genre genre : genres){
             movie. removeGenre(genre);
         }

        List<Actor> actors = new LinkedList<>();
        movie.getActors().iterator().forEachRemaining(actors::add);

        for(Actor actor : actors){
            movie.removeActor(actor);
        }

         movieRepository.save(movie);
         movieRepository.deleteById(id);
    }

    @Override
    public Movie findByTitle(String title) {
        Optional<Movie> movieOptional = movieRepository.findByTitle(title);
        if(movieOptional.isEmpty()){
             throw new NotFoundException("Movie with title " + title + " not found");
        }
        return movieOptional.get();
    }

    @Override
    public List<Movie> findByGenre(String genreName) {
        Optional<Genre> genreOptional = genreRepository.findGenreByName(genreName);
        if(!genreOptional.isPresent()){
            throw new NotFoundException("Genre with name " + genreName + " not found");
        }
        List<Movie> movies = genreOptional.get().getMovies();
        return movies;
    }


}
