package com.example.projectmovie.repositories;

import com.example.projectmovie.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @Order(1)
    public void addMovie(){
        Movie movie = new Movie();
        movie.setTitle("title1");
        movie.setReleaseDate(LocalDate.of(2000, 11, 12));
        movieRepository.save(movie);
        assertNotNull(movie);
        assertEquals(4L, movie.getId());
    }

    @Test
    @Order(2)
    public void findMovieByTitle(){
        Optional<Movie> optionalMovie = movieRepository.findByTitle("title1");
        assertFalse(optionalMovie.isEmpty());
        log.info("findMovieByTitle...");
        log.info(optionalMovie.get().getTitle());
    }

    @ParameterizedTest
    @Order(3)
    @ValueSource(longs = {10})
    public void findMovieById(long id){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        assertTrue(optionalMovie.isEmpty());
    }


}
