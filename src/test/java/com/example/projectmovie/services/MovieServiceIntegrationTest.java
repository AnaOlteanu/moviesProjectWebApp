package com.example.projectmovie.services;

import com.example.projectmovie.ProjectMovieApplication;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.domain.MovieType;
import com.example.projectmovie.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProjectMovieApplication.class})
@Transactional
@Slf4j
@ActiveProfiles("h2")
public class MovieServiceIntegrationTest {
    @Autowired
    MovieService movieService;

    @Test
    public void findAll(){
        List<Movie> result = movieService.findAll();
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Titanic", result.get(0).getTitle());
        result.forEach(movie -> log.info(movie.getTitle()));
    }

    @Test
    public void findByIdHappyFlow(){
        Long movieId = 1l;
        Movie result = movieService.findById(movieId);
        assertNotNull(result);
        assertEquals("Titanic", result.getTitle());
        assertEquals(MovieType.LONG, result.getMovieInfo().getMovieType());
    }

    @Test
    public void findByIdNotFound(){
        Long movieId = 10l;
        NotFoundException result = assertThrows(NotFoundException.class, () -> movieService.findById(movieId));
        assertNotNull(result);
        assertEquals("Movie with id 10 not found", result.getMessage());
    }

    @Test
    public void deleteById(){
        Long movieId = 1l;
        movieService.deleteById(movieId);

        NotFoundException result = assertThrows(NotFoundException.class, () -> movieService.findById(movieId));
        assertNotNull(result);
        assertEquals("Movie with id 1 not found", result.getMessage());
    }

    @Test
    public void findByGenreHappyFlow(){
        List<Movie> movies = movieService.findByGenre("romance");
        assertNotNull(movies);
        assertEquals(3, movies.size());
        assertEquals("Titanic", movies.get(0).getTitle());
        log.info("Find movies by genre");
        movies.forEach(movie -> log.info("Movie with title: {} ", movie.getTitle()));
    }

    @Test
    public void findByGenreNotFound(){
        NotFoundException result = assertThrows(NotFoundException.class, () -> movieService.findByGenre("genre"));
        assertNotNull(result);
        assertEquals("Genre with name genre not found", result.getMessage());

    }

}
