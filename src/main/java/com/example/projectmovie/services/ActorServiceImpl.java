package com.example.projectmovie.services;

import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.Genre;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.exception.NotFoundException;
import com.example.projectmovie.repositories.ActorRepository;
import com.example.projectmovie.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService{
    ActorRepository actorRepository;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository){
        this.actorRepository = actorRepository;
    }

    @Override
    public List<Actor> findAll() {
        List<Actor> actors = new ArrayList<>();
        actorRepository.findAll().iterator().forEachRemaining(actors::add);
        return actors;
    }

    @Override
    public Actor findById(Long id) {
        Optional<Actor> actorOptional = actorRepository.findById(id);
        if(actorOptional.isPresent()){
            return actorOptional.get();
        } else {
            throw new NotFoundException("Actor with id " + id + " not found");
        }
    }

    @Override
    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Actor> actorOptional = actorRepository.findById(id);
        if(!actorOptional.isPresent()){
            throw new NotFoundException("Actor with id " + id + " not found");
        }

        Actor actor = actorOptional.get();
        List<Movie> movies = new LinkedList<>();
        actor.getMovies().iterator().forEachRemaining(movies::add);

        for(Movie movie : movies){
            actor.removeMovie(movie);
        }

        actorRepository.save(actor);
        actorRepository.deleteById(id);
    }

    @Override
    public List<Actor> findByName(String name) {
        return actorRepository.findByName(name);
    }
}
