package com.example.projectmovie.controllers;

import com.example.projectmovie.domain.*;
import com.example.projectmovie.repositories.ActorRepository;
import com.example.projectmovie.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class ActorController {

    @Autowired
    ActorService actorService;

    @Autowired
    GenreService genreService;

    @Autowired
    ContactInfoService contactInfoService;

    @Autowired
    ImageService imageService;

    @RequestMapping("/actor/list")
    public ModelAndView actorsList(){
        ModelAndView modelAndView = new ModelAndView("actors");
        List<Actor> actors = actorService.findAll();
        List<Genre> genres = genreService.findAll();

        modelAndView.addObject("actors",actors);
        modelAndView.addObject("genres",genres);
        return modelAndView;
    }

    @RequestMapping("/actor/new")
    public String getNewActor(Model model) {
        model.addAttribute("actor", new Actor());

        return "actor-add";
    }

    @RequestMapping("/actor/edit/{id}")
    public String editActor(@PathVariable String id, Model model){
        model.addAttribute("actor", actorService.findById(Long.valueOf(id)));
        return "actor-add";
    }

    @PostMapping("/actor")
    public String saveActor(@Valid @ModelAttribute Actor actor,
                            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("Error during adding actor: {} ", bindingResult.getFieldErrors());
            return "actor-add";
        }

        if(actor.getId() != null) {
            Actor existingActor = actorService.findById(actor.getId());
            ContactInfo contactInfo = existingActor.getContactInfo();
            if (contactInfo != null) {
                actor.setContactInfo(contactInfo);
            }
        }

        actorService.save(actor);

        return "redirect:/actor/" + actor.getId();
    }

    @GetMapping("/actor/{id}")
    public String retrieveActorById(@PathVariable long id, Model model){
        model.addAttribute("actor", actorService.findById(id));
        return "actor-info";
    }

    @GetMapping("actor/contactInfo/{id}")
    public ModelAndView newActorInfo(@PathVariable Long id){
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setActor(actorService.findById(id));

        ModelAndView modelAndView = new ModelAndView("actor-info-add");
        modelAndView.addObject("actorInfo", contactInfo);

        return modelAndView;
    }

    @GetMapping("actor/editContactInfo/{id}")
    public String editActorInfo(@PathVariable Long id, Model model){
        model.addAttribute("actorInfo", contactInfoService.findById(id));
        return "actor-info-add";
    }


    @PostMapping("actor/contactInfo")
    public String addActorInfo(@ModelAttribute("actorInfo") @Valid ContactInfo contactInfo,
                               BindingResult bindingResult
//                               @RequestParam("imagefile") MultipartFile file
                             ){

        if(bindingResult.hasErrors()){
            log.info("Error during adding actor info {} ", bindingResult.getFieldErrors());
            return "actor-info-add";
        }

        if(contactInfo.getId() != null){
            ContactInfo existingContactInfo = contactInfoService.findById(contactInfo.getId());
            contactInfo.setActor(existingContactInfo.getActor());
        }

        ContactInfo contactInfo1 = contactInfoService.save(contactInfo);
//        imageService.saveImageFileForActor(Long.valueOf(contactInfo1.getActor().getId()), file);

        return "redirect:/actor/" + contactInfo.getActor().getId();

    }

    @RequestMapping("/actor/delete/{id}")
    public String deleteById(@PathVariable String id){
        actorService.deleteById(Long.valueOf(id));
        return "redirect:/actor/list";
    }

    @RequestMapping("/actor/search")
    public String searchActor(@RequestParam("searchInput") String searchInput,
                              Model model){
        List<Actor> actorList = actorService.findByName(searchInput);
        List<Genre> genres = genreService.findAll();
        model.addAttribute("actors", actorList);
        model.addAttribute("genres", genres);
        return "actors";
    }


}
