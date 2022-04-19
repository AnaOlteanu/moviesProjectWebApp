package com.example.projectmovie.controllers;

import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.Genre;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.domain.MovieInfo;
import com.example.projectmovie.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class MovieController {

    Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    MovieService movieService;

    @Autowired
    ActorService actorService;

    @Autowired
    GenreService genreService;

    @Autowired
    MovieInfoService movieInfoService;

    @Autowired
    ImageService imageService;


    @GetMapping("/movie/list")
    public ModelAndView movieList(@RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);
        List<Genre> genres = genreService.findAll();
        ModelAndView modelAndView = new ModelAndView("movies");
        modelAndView.addObject("genres",genres);
        Page<Movie> moviePage = movieService.findAllPaginated(PageRequest.of(currentPage - 1, pageSize));
        modelAndView.addObject("movies", moviePage);
        return modelAndView;
    }

    @GetMapping("/movie/{id}")
    public String retrieveInfoById(@PathVariable Long id, Model model){
        model.addAttribute("movie", movieService.findById(id));
        return "movie-info";
    }

    @RequestMapping("/movie/new")
    public String getNewMovie(Model model) {
        model.addAttribute("movie", new Movie());
        List<Genre> genresAll = genreService.findAll();
        List<Actor> actorsAll = actorService.findAll();

        model.addAttribute("genresAll", genresAll);
        model.addAttribute("actorsAll", actorsAll);
        return "movie-add";
    }

    @RequestMapping("/movie/edit/{id}")
    public String editMovie(@PathVariable String id, Model model){
        model.addAttribute("movie", movieService.findById(Long.valueOf(id)));
        List<Genre> genresAll = genreService.findAll();
        List<Actor> actorsAll = actorService.findAll();
        model.addAttribute("genresAll", genresAll);
        model.addAttribute("actorsAll", actorsAll);
        return "movie-add";
    }

    @PostMapping("/movie")
    public String saveMovie(@Valid @ModelAttribute Movie movie,
                            BindingResult bindingResult,
                            Model model
                            ){
        if(bindingResult.hasErrors()){
            List<Genre> genresAll = genreService.findAll();
            List<Actor> actorsAll = actorService.findAll();

            model.addAttribute("genresAll", genresAll);
            model.addAttribute("actorsAll", actorsAll);
            return "movie-add";
        }
        if(movie.getId() != null) {
            Movie existingMovie = movieService.findById(movie.getId());
            MovieInfo movieInfo = existingMovie.getMovieInfo();
            if (movieInfo != null) {
                movie.setMovieInfo(movieInfo);
            }
        }

        Movie savedMovie = movieService.save(movie);

        return "redirect:/movie/" + movie.getId();
    }

    @RequestMapping("/movie/delete/{id}")
    public String deleteById(@PathVariable String id){
        movieService.deleteById(Long.valueOf(id));
        return "redirect:/movie/list";
    }


    @GetMapping("movie/movieInfo/{title}")
    public ModelAndView newMovieInfo(@PathVariable String title){
        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setMovie(movieService.findByTitle(title));

        ModelAndView modelAndView = new ModelAndView("movie-info-add");
        modelAndView.addObject("movieInfo", movieInfo);

        return modelAndView;
    }

    @GetMapping("/movie/movieInfoEdit/{id}")
    public String editMovieInfo(@PathVariable("id") Long id, Model model){
        model.addAttribute("movieInfo", movieInfoService.findById(id));
        return "movie-info-add";
    }

    @PostMapping("movie/movieInfo")
    public String addMovieInfo(@ModelAttribute("movieInfo") @Valid MovieInfo movieInfo,
                               BindingResult bindingResult,
                               @RequestParam("imagefile") MultipartFile file){

        if(bindingResult.hasErrors()){
            return "movie-info-add";
        }

        if(movieInfo.getId() != null){
            MovieInfo existingMovieInfo = movieInfoService.findById(movieInfo.getId());
            movieInfo.setMovie(existingMovieInfo.getMovie());
        }
        MovieInfo movieInfo1 = movieInfoService.save(movieInfo);
        imageService.saveImageFileForMovie(Long.valueOf(movieInfo1.getMovie().getId()), file, movieInfo);

        return "redirect:/movie/" + movieInfo.getMovie().getId();

    }

    @GetMapping("/movie")
    public ModelAndView getMoviesByGenre(@RequestParam("genre") String genreName){
        ModelAndView modelAndView = new ModelAndView("movies-by-genre");
        List<Movie> movies = movieService.findByGenre(genreName);
        List<Genre> genres = genreService.findAll();
        modelAndView.addObject("genres", genres);
        modelAndView.addObject("movies", movies);
        modelAndView.addObject("genre", genreName);
        return modelAndView;
    }

    @GetMapping("/movie/editGenres/{id}")
    public ModelAndView editMovieGenresView(@PathVariable("id") Long movieId){
        ModelAndView modelAndView = new ModelAndView("movie-genre-edit");
        Movie movie = movieService.findById(movieId);
        List<Genre> genres = genreService.findAll();
        modelAndView.addObject("genres", genres);
        modelAndView.addObject("movie", movie);
        return modelAndView;
    }

    @PostMapping("/movie/editGenres/{id}")
    public String editMovieGenres(@ModelAttribute @Valid Movie movie,
                                  BindingResult bindingResult,
                                  @PathVariable Long id){
        if(bindingResult.hasErrors()){
            return "movie-genre-edit";
        }

        Movie existingMovie = movieService.findById(id);
        movie.setTitle(existingMovie.getTitle());
        movie.setReleaseDate(existingMovie.getReleaseDate());
        movie.setActors(existingMovie.getActors());
        movie.setMovieInfo(existingMovie.getMovieInfo());
        movieService.save(movie);
        return "redirect:/movie/" + id;
    }

}
