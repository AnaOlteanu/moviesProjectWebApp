package com.example.projectmovie.controllers;

import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.ContactInfo;
import com.example.projectmovie.domain.Gender;
import com.example.projectmovie.domain.Genre;
import com.example.projectmovie.services.ActorService;
import com.example.projectmovie.services.ContactInfoService;
import com.example.projectmovie.services.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
public class ActorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ActorService actorService;

    @MockBean
    ContactInfoService contactInfoService;

    @MockBean
    GenreService genreService;

    @MockBean
    Model model;

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "GUEST")
    public void saveActorNotAllowed() throws Exception {
        Actor actor = new Actor();
        actor.setId(10l);
        actor.setLastName("LastName");
        actor.setFirstName("FirstName");
        actor.setContactInfo(null);
        actor.setMovies(null);

        mockMvc.perform(get("/actor/new"))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "ADMIN")
    public void saveActorAllowed() throws Exception {
        Actor actor = new Actor();
        actor.setId(10l);
        actor.setLastName("LastName");
        actor.setFirstName("FirstName");
        actor.setContactInfo(null);
        actor.setMovies(null);

        mockMvc.perform(get("/actor/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("actor-add"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "ADMIN")
    public void editActorInfoAllowed() throws Exception {
        Actor actor = new Actor();
        actor.setId(10l);
        actor.setLastName("LastName");
        actor.setFirstName("FirstName");
        actor.setMovies(null);
        ContactInfo contactInfo = new ContactInfo();
        Long contactInfoId = 10l;
        contactInfo.setId(contactInfoId);
        contactInfo.setGender(Gender.F);
        contactInfo.setDateOfBirth(LocalDate.of(1999, 04, 21));
        contactInfo.setActor(actor);

        when(contactInfoService.findById(contactInfoId)).thenReturn(contactInfo);

        mockMvc.perform(get("/actor/editContactInfo/{id}", contactInfoId))
                .andExpect(status().isOk())
                .andExpect(view().name("actor-info-add"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "ADMIN")
    public void searchActorTest() throws Exception{
        Actor actor = new Actor();
        actor.setId(10l);
        actor.setLastName("LastName");
        actor.setFirstName("FirstName");
        actor.setMovies(null);
        actor.setContactInfo(null);
        List<Actor> actors = Arrays.asList(actor);
        Genre genre = new Genre();
        genre.setId(10l);
        genre.setName("genre1");
        genre.setMovies(null);
        List<Genre> genres = Arrays.asList(genre);

        when(actorService.findByName("a")).thenReturn(actors);
        when(genreService.findAll()).thenReturn(genres);

        mockMvc.perform(get("/actor/search")
                .param("searchInput", "a"))
                .andExpect(status().isOk())
                .andExpect(view().name("actors"))
                .andExpect(model().attribute("actors", actors))
                .andExpect(model().attribute("genres", genres));
    }
}
