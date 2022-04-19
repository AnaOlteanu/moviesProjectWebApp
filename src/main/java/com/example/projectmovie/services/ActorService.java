package com.example.projectmovie.services;

import com.example.projectmovie.domain.Actor;

import java.util.List;

public interface ActorService {
    List<Actor> findAll();

    Actor findById(Long id);

    Actor save(Actor actor);

    void deleteById(Long id);

    List<Actor> findByName(String name);
}
