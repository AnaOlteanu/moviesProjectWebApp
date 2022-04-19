package com.example.projectmovie.services;

import com.example.projectmovie.ProjectMovieApplication;
import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.Gender;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.domain.MovieType;
import com.example.projectmovie.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProjectMovieApplication.class})
@Transactional
@Slf4j
@ActiveProfiles("h2")
public class ActorServiceIntegrationTest {
    @Autowired
    ActorService actorService;

    @Test
    public void findAll(){
        List<Actor> result = actorService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Angelina", result.get(1).getFirstName());
        assertEquals(Gender.F, result.get(1).getContactInfo().getGender());
    }

    @Test
    public void findByIdHappyFlow(){
        Long actorId = 2l;
        Actor result = actorService.findById(actorId);
        assertNotNull(result);
        assertEquals("Angelina", result.getFirstName());
        assertEquals(Gender.F, result.getContactInfo().getGender());
    }

    @Test
    public void findByIdNotFound(){
        Long actorId = 10l;
        NotFoundException result = assertThrows(NotFoundException.class, () -> actorService.findById(actorId));
        assertNotNull(result);
        assertEquals("Actor with id 10 not found", result.getMessage());
    }

    @Test
    public void deleteById(){
        Long actorId = 1l;
        actorService.deleteById(actorId);

        NotFoundException result = assertThrows(NotFoundException.class, () -> actorService.findById(actorId));
        assertNotNull(result);
        assertEquals("Actor with id 1 not found", result.getMessage());
    }
}
