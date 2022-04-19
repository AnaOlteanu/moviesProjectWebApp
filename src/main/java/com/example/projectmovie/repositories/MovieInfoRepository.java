package com.example.projectmovie.repositories;


import com.example.projectmovie.domain.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieInfoRepository extends JpaRepository<MovieInfo, Long> {
}
