package com.example.projectmovie.services;

import com.example.projectmovie.domain.ContactInfo;

public interface ContactInfoService {
    ContactInfo save(ContactInfo actorInfo);

    void deleteContactInfo(Long id);

    ContactInfo findById(Long id);
}
