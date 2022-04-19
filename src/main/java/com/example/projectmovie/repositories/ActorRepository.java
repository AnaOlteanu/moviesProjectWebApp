package com.example.projectmovie.repositories;

import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends PagingAndSortingRepository<Actor, Long> {
    @Query("SELECT a FROM Actor a WHERE a.firstName LIKE %:name% OR a.lastName LIKE %:name%")
    List<Actor> findByName(@Param("name") String name);


}
