package com.example.projectmovie.entitymanager;

import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.Movie;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntityManagerTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    public void orphanRemoval(){
        Actor actor = entityManager.find(Actor.class, 1l);
        actor.setContactInfo(null);
        entityManager.persist(actor);
        entityManager.flush();
    }

    @Test
    public void updateActor(){
        Actor actor = entityManager.find(Actor.class, 1l);
        actor.setLastName("ana");
        entityManager.persist(actor);
        entityManager.flush();
    }

    @Test
    public void findMovie(){
        Movie movie = entityManager.find(Movie.class, 1l);
        assertNotNull(movie);
        assertEquals("Titanic", movie.getTitle());
    }

    @Test
    public void saveMovie(){
        Actor actor = new Actor();
        actor.setFirstName("ana");
        actor.setLastName("olteanu");
        Movie movie = new Movie();
        movie.setTitle("title");
        movie.setReleaseDate(LocalDate.of(2000,10,22));
        movie.setMovieInfo(null);
        movie.setActors(Arrays.asList(actor));

        entityManager.persist(movie);
        entityManager.flush();
        entityManager.clear();
    }

}
