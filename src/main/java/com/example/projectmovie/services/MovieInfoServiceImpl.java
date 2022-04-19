package com.example.projectmovie.services;

import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.domain.MovieInfo;
import com.example.projectmovie.exception.NotFoundException;
import com.example.projectmovie.repositories.MovieInfoRepository;
import com.example.projectmovie.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieInfoServiceImpl implements MovieInfoService{
    MovieInfoRepository movieInfoRepository;

    @Autowired
    public MovieInfoServiceImpl(MovieInfoRepository movieInfoRepository){
        this.movieInfoRepository = movieInfoRepository;
    }

    @Override
    public MovieInfo save(MovieInfo movieInfo) {
        return movieInfoRepository.save(movieInfo);
    }

    @Override
    public void deleteMovieInfo(Long id) {
        Optional<MovieInfo> movieInfoOptional = movieInfoRepository.findById(id);
        if(!movieInfoOptional.isPresent()){
            throw new NotFoundException("Movie info with id " + id + " not found!");
        }
        MovieInfo movieInfo = movieInfoOptional.get();
        Movie movie = movieInfo.getMovie();
        movieInfo.removeMovie(movie);

        movieInfoRepository.save(movieInfo);
        movieInfoRepository.deleteById(id);

    }

    @Override
    public MovieInfo findById(Long id) {
        Optional<MovieInfo> movieInfoOptional = movieInfoRepository.findById(id);
        if(!movieInfoOptional.isPresent()){
            throw new NotFoundException("Movie info with id " + id + " not found!");
        }
        return movieInfoOptional.get();
    }
}
