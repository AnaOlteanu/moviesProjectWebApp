package com.example.projectmovie.services;

import com.example.projectmovie.domain.Genre;
import com.example.projectmovie.exception.NotFoundException;
import com.example.projectmovie.repositories.GenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GenreServiceImpl implements GenreService{
    GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        genreRepository.findAll().iterator().forEachRemaining(genres::add);
        return genres;
    }

    @Override
    public Genre findById(Long id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        if(!genreOptional.isPresent()){
            throw new NotFoundException("Genre with id " + id + " not found");
        }
        return genreOptional.get();
    }

    @Override
    public Genre save(Genre genre) {
        Genre savedGenre = genreRepository.save(genre);
        log.info("Save genre {} ", savedGenre);
        return savedGenre;
    }
}
