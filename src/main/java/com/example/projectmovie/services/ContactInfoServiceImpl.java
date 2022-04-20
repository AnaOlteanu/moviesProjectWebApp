package com.example.projectmovie.services;

import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.ContactInfo;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.domain.MovieInfo;
import com.example.projectmovie.exception.NotFoundException;
import com.example.projectmovie.repositories.ActorRepository;
import com.example.projectmovie.repositories.ContactInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ContactInfoServiceImpl implements ContactInfoService{

    ContactInfoRepository contactInfoRepository;

    @Autowired
    public ContactInfoServiceImpl(ContactInfoRepository contactInfoRepository){
        this.contactInfoRepository = contactInfoRepository;
    }

    @Override
    public ContactInfo save(ContactInfo actorInfo) {
        ContactInfo savedActorInfo = contactInfoRepository.save(actorInfo);
        log.info("Save contact info {} ", savedActorInfo);
        return savedActorInfo;
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
        log.info("Delete contact info with id {} ", id);
        contactInfoRepository.save(contactInfo);
        contactInfoRepository.deleteById(id);
    }

    @Override
    public ContactInfo findById(Long id) {
        Optional<ContactInfo> contactInfoOptional = contactInfoRepository.findById(id);
        if(!contactInfoOptional.isPresent()){
            log.info("Contact info with id {} not found", id);
            throw new NotFoundException("Actor info with id " + id + " not found!");
        }
        log.info("Contact info with id {} found", id);
        return contactInfoOptional.get();
    }
}
