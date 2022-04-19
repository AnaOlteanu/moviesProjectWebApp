package com.example.projectmovie.controllers;

import com.example.projectmovie.domain.Genre;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.exception.NotFoundException;
import com.example.projectmovie.services.GenreService;
import com.example.projectmovie.services.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
public class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    @MockBean
    GenreService genreService;

    @Test
    @WithMockUser(username = "guest", password = "guest", roles = "GUEST")
    public void getMovieById() throws Exception {
        Long movieId = 1l;
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("title1");
        movie.setReleaseDate(LocalDate.of(2000, 11, 12));

        when(movieService.findById(movieId)).thenReturn(movie);

        mockMvc.perform(get("/movie/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie-info"))
                .andExpect(model().attribute("movie", movie));

    }

    @Test
    @WithMockUser(username = "user_1", password = "pass_1", roles = "GUEST")
    public void getMovieByIdNotFound() throws Exception {
        Long movieId = 999l;
        when(movieService.findById(movieId)).thenThrow(new NotFoundException("Movie not found!"));

        mockMvc.perform(get("/movie/{id}", movieId))
                .andExpect(status().isNotFound())
                .andExpect(view().name("notfound"));
    }

    @Test
    @WithMockUser(username = "guest", password = "guest", roles = "GUEST")
    public void deleteMovieForbidden() throws Exception {
        mockMvc.perform(get("/movie/delete/{id}", "1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void deleteMovieAllowed() throws Exception {
        mockMvc.perform(get("/movie/delete/{id}", "2"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void addMovie() throws Exception{
        Long movieId = 1l;
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("title1");
        movie.setReleaseDate(LocalDate.of(2000, 11, 12));
        movie.setMovieInfo(null);
        movie.setActors(null);
        movie.setGenres(null);
        MockMultipartFile images
                = new MockMultipartFile(
                "images",
                "image.jpg",
                MediaType.IMAGE_PNG_VALUE,
                "content".getBytes()
        );

        when(movieService.save(movie)).thenReturn(movie);

        mockMvc.perform(multipart("/movie").file(images)
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("movie", movie)
                .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/movie/" + movie.getId()));
    }

    @Test
    @WithMockUser(username = "user_1", password = "pass_1", roles = "GUEST")
    public void editMovieGenres() throws Exception {
        Long movieId = 10L;
        Movie movie = new Movie();
        movie.setTitle("title");
        movie.setReleaseDate(LocalDate.of(2000, 11, 12));
        movie.setId(movieId);
        List<Genre> genres = new ArrayList<>();
        Long genreId = 10l;
        genres.add(new Genre(genreId, "drama"));

        when(movieService.findById(movieId)).thenReturn(movie);
        when(genreService.findAll()).thenReturn(genres);

        mockMvc.perform(get("/movie/editGenres/{id}", movieId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("movie-genre-edit"))
                .andExpect(model().attribute("genres", genres))
                .andExpect(model().attribute("movie", movie));
    }

}
