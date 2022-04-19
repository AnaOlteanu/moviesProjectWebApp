package com.example.projectmovie.services;

import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.Genre;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.exception.NotFoundException;
import com.example.projectmovie.repositories.GenreRepository;
import com.example.projectmovie.repositories.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("h2")
public class MovieServiceTest {
    @Mock
    MovieRepository movieRepository;

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    MovieServiceImpl movieService;

    @Test
    public void findAllTest() {
        Movie movie1 = new Movie();
        movie1.setId(10l);
        movie1.setTitle("title1");
        movie1.setReleaseDate(LocalDate.of(2000,12,12));
        movie1.setActors(null);
        movie1.setMovieInfo(null);
        movie1.setGenres(null);
        Movie movie2 = new Movie();
        movie2.setId(11l);
        movie2.setTitle("title2");
        movie2.setReleaseDate(LocalDate.of(2000,12,12));
        movie2.setActors(null);
        movie2.setMovieInfo(null);
        movie2.setGenres(null);

        List<Movie> movies = Arrays.asList(movie1, movie2);

        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(movie1.getTitle(), result.get(0).getTitle());
        verify(movieRepository, times(1)).findAll();

    }

    @Test
    public void findMovieByIdHappyFlow(){
        Movie movie1 = new Movie();
        movie1.setId(10l);
        movie1.setTitle("title1");
        movie1.setReleaseDate(LocalDate.of(2000,12,12));
        movie1.setActors(null);
        movie1.setMovieInfo(null);
        movie1.setGenres(null);

        when(movieRepository.findById(movie1.getId())).thenReturn(Optional.of(movie1));

        Movie result = movieService.findById(movie1.getId());

        assertNotNull(result);
        assertEquals(movie1.getTitle(), result.getTitle());
        assertEquals(movie1.getReleaseDate(), result.getReleaseDate());
    }

    @Test
    public void findMovieByIdNotFound(){
        Long movieId = 10l;
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());
        NotFoundException result = assertThrows(NotFoundException.class, () -> movieService.findById(movieId));
        assertEquals("Movie with id " + movieId + " not found", result.getMessage());
    }

    @Test
    public void findMovieByGenreHappyFlow(){
        Movie movie1 = new Movie();
        movie1.setId(10l);
        movie1.setTitle("title1");
        movie1.setReleaseDate(LocalDate.of(2000,12,12));
        movie1.setActors(null);
        movie1.setMovieInfo(null);
        Long genreId = 10l;
        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setName("genre1");
        genre.setMovies(Arrays.asList(movie1));
        when(genreRepository.findGenreByName("genre1")).thenReturn(Optional.of(genre));
        List<Movie> result = movieService.findByGenre(genre.name);
        assertEquals(1, result.size());
        assertEquals(movie1.getTitle(), result.get(0).getTitle());
    }

    @Test
    public void findMovieByGenreNotFound(){
        when(genreRepository.findGenreByName("genre1")).thenReturn(Optional.empty());
        NotFoundException result = assertThrows(NotFoundException.class, () -> movieService.findByGenre("genre1"));
        assertEquals("Genre with name genre1 not found", result.getMessage());
    }

    @Test
    public void deleteMovieById(){
        Genre genre = new Genre();
        genre.setId(10l);
        genre.setName("genre1");

        Actor actor = new Actor();
        actor.setId(10l);
        actor.setFirstName("firstname");
        actor.setLastName("lastname");

        Movie movie1 = new Movie();
        movie1.setId(10l);
        movie1.setTitle("title1");
        movie1.setReleaseDate(LocalDate.of(2000,12,12));
        movie1.setActors(Arrays.asList(actor));
        movie1.setGenres(Arrays.asList(genre));
        movie1.setMovieInfo(null);

        Movie movie2 = new Movie();
        movie2.setId(10l);
        movie2.setTitle("title1");
        movie2.setReleaseDate(LocalDate.of(2000,12,12));
        movie2.setActors(null);
        movie2.setGenres(null);
        movie2.setMovieInfo(null);

        when(movieRepository.findById(10l)).thenReturn(Optional.of(movie1));
        when(movieRepository.save(movie1)).thenReturn(movie2);
        doNothing().when(movieRepository).deleteById(10l);

        movieService.deleteById(10l);

        verify(movieRepository, times(1)).deleteById(10l);

    }
}
