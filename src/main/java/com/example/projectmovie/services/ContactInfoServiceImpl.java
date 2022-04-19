package com.example.projectmovie.services;

import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.ContactInfo;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.domain.MovieInfo;
import com.example.projectmovie.exception.NotFoundException;
import com.example.projectmovie.repositories.ActorRepository;
import com.example.projectmovie.repositories.ContactInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactInfoServiceImpl implements ContactInfoService{

    ContactInfoRepository contactInfoRepository;

    @Autowired
    public ContactInfoServiceImpl(ContactInfoRepository contactInfoRepository){
        this.contactInfoRepository = contactInfoRepository;
    }

    @Override
    public ContactInfo save(ContactInfo actorInfo) {
        return contactInfoRepository.save(actorInfo);
    }

    @Override
    public void deleteContactInfo(Long id) {
        Optional<ContactInfo> contactInfoOptional = contactInfoRepository.findById(id);
        if(!contactInfoOptional.isPresent()){
            throw new NotFoundException("Actor info with id " + id + " not found!");
        }
        ContactInfo contactInfo = contactInfoOptional.get();
        Actor actor = contactInfo.getActor();
        contactInfo.removeActor(actor);

        contactInfoRepository.save(contactInfo);
        contactInfoRepository.deleteById(id);
    }

    @Override
    public ContactInfo findById(Long id) {
        Optional<ContactInfo> contactInfoOptional = contactInfoRepository.findById(id);
        if(!contactInfoOptional.isPresent()){
            throw new NotFoundException("Actor info with id " + id + " not found!");
        }
        return contactInfoOptional.get();
    }
}
