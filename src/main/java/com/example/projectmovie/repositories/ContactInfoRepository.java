package com.example.projectmovie.repositories;

import com.example.projectmovie.domain.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
}
