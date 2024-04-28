package com.movies.catalog.controller;

import com.movies.catalog.entity.Movie;
import com.movies.catalog.service.MovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all the movies")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movie found", content = {
            @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Movie.class)))
            })
    })
    @GetMapping(value = "/movies", produces = "application/json")
    public ResponseEntity<List<Movie>> findAll() {
        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get a movie by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movie found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class)) }),
        @ApiResponse(responseCode = "204", description = "Movie not found", 
        content = @Content)
    })
    @GetMapping(value = "/movies/{id}", produces = "application/json")
    public ResponseEntity<Movie> findById(
            @Parameter(description = "Id of the movie to be searched") @PathVariable Long id) {
        Optional<Movie> movie = movieService.findById(id);

        return movie.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Operation(summary = "Add a new Movie to the catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class)) }),
            @ApiResponse(responseCode = "400", description = "Movie information is invalid", 
            content = @Content),
            @ApiResponse(responseCode = "409", description = "Movie already exists", 
            content = @Content)
        })
    @PostMapping(value = "/movies", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Movie> addMovie(
            @Parameter(description = "Movie information") @RequestBody @Valid Movie movie) {
        return new ResponseEntity<>(movieService.addNew(movie), HttpStatus.CREATED);
    }
}
