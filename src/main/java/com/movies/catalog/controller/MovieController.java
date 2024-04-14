package com.movies.catalog.controller;

import com.movies.catalog.entity.Movie;
import com.movies.catalog.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {
    @Autowired
    MovieService movieService;

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> findAll() {
        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> findById(@PathVariable Long id) {
        Optional<Movie> movie = movieService.findById(id);

        return movie.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody  @Valid Movie movie) {
        return new ResponseEntity<>(movieService.addNew(movie), HttpStatus.OK);
    }
}
