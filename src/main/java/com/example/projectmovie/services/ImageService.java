package com.example.projectmovie.services;

import com.example.projectmovie.domain.MovieInfo;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFileForMovie(Long productId, MultipartFile file, MovieInfo movieInfo);

//    void saveImageFileForActor(Long valueOf, MultipartFile file);
}
