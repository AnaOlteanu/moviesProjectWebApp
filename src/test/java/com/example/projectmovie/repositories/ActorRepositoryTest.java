package com.example.projectmovie.repositories;

import com.example.projectmovie.domain.Actor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ActorRepositoryTest {

    @Autowired
    ActorRepository actorRepository;

    @Test
    public void findAllActors(){
        List<Actor> actors = new ArrayList<>();
        actorRepository.findAll().iterator().forEachRemaining(actors::add);
        assertFalse(actors.isEmpty());
        log.info("findAllActors");
        actors.forEach(actor -> log.info(actor.getFirstName() + " " + actor.getLastName()));

    }

    @Test
    public void findActorByName(){
        List<Actor> actors = actorRepository.findByName("an");
        assertFalse(actors.isEmpty());
        log.info("findActorByName...");
        actors.forEach(actor -> log.info(actor.getFirstName() + " " + actor.getLastName()));
    }
}
