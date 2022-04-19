package com.example.projectmovie.services;

import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.ContactInfo;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.domain.MovieInfo;
import com.example.projectmovie.repositories.ActorRepository;
import com.example.projectmovie.repositories.ContactInfoRepository;
import com.example.projectmovie.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService{
    MovieRepository movieRepository;
    ActorRepository actorRepository;
    ContactInfoRepository contactInfoRepository;

    @Autowired
    public ImageServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository, ContactInfoRepository contactInfoRepository) {

        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.contactInfoRepository = contactInfoRepository;
    }

    @Override
    @Transactional
    public void saveImageFileForMovie(Long movieId, MultipartFile file, MovieInfo movieInfo) {
        try {
            Movie movie = movieRepository.findById(movieId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0; for (byte b : file.getBytes()){
                byteObjects[i++] = b; }

            MovieInfo info = movie.getMovieInfo();
            if (info == null) {
                info = movieInfo;
            }

            info.setImage(byteObjects);
            movie.setMovieInfo(info);
            info.setMovie(movie);
            movieRepository.save(movie); }
        catch (IOException e) {
        }
    }

//    @Override
//    @Transactional
//    public void saveImageFileForActor(Long actorId, MultipartFile file) {
//        try {
//            Actor actor = actorRepository.findById(actorId).get();
//
//            Byte[] byteObjects = new Byte[file.getBytes().length];
//            int i = 0; for (byte b : file.getBytes()){
//                byteObjects[i++] = b; }
//
//            ContactInfo info = actor.getContactInfo();
//            if (info == null) {
//                info = new ContactInfo();
//            }
//
//            info.setImage(byteObjects);
//            actor.setContactInfo(info);
//            info.setActor(actor);
//            actorRepository.save(actor); }
//        catch (IOException e) {
//        }
//    }
}
