package com.example.projectmovie.controllers;

import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.ContactInfo;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.domain.MovieInfo;
import com.example.projectmovie.services.ActorService;
import com.example.projectmovie.services.ImageService;
import com.example.projectmovie.services.MovieService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final MovieService movieService;
    private final ActorService actorService;


    public ImageController(@Autowired ImageService imageService, @Autowired MovieService movieService, @Autowired ActorService actorService) {
        this.movieService = movieService;
        this.actorService = actorService;
    }

    @GetMapping("movie/image/{id}")
    public void downloadImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        Movie movie = movieService.findById(Long.valueOf(id));
        if (movie.getMovieInfo() != null) {
            MovieInfo movieInfo = movie.getMovieInfo();

            if (movie.getMovieInfo().getImage() != null) {
                byte[] byteArray = new byte[movieInfo.getImage().length];
                int i = 0;
                for (Byte wrappedByte : movieInfo.getImage()) {
                    byteArray[i++] = wrappedByte;
                }
                response.setContentType("image/jpeg");
                InputStream is = new ByteArrayInputStream(byteArray);
                try {
                    IOUtils.copy(is, response.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    @GetMapping("actor/image/{id}")
//    public void actorImage(@PathVariable String id, HttpServletResponse response) throws IOException {
//        Actor actor = actorService.findById(Long.valueOf(id));
//        if (actor.getContactInfo() != null) {
//            ContactInfo contactInfo = actor.getContactInfo();
//
//            if (actor.getContactInfo().getImage() != null) {
//                byte[] byteArray = new byte[contactInfo.getImage().length];
//                int i = 0;
//                for (Byte wrappedByte : contactInfo.getImage()) {
//                    byteArray[i++] = wrappedByte;
//                }
//                response.setContentType("image/jpeg");
//                InputStream is = new ByteArrayInputStream(byteArray);
//                try {
//                    IOUtils.copy(is, response.getOutputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
