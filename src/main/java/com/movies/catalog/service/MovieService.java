package com.movies.catalog.service;

import com.movies.catalog.entity.Movie;
import com.movies.catalog.exception.MovieAlreadyExistsException;
import com.movies.catalog.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(final Long id) {
        return movieRepository.findById(id);
    }

    public Movie addNew(final Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findByTitleAndReleaseDate(movie.getTitle(), movie.getReleaseDate());

        if (existingMovie.isPresent()) {
            throw new MovieAlreadyExistsException();
        } else {
            return movieRepository.save(movie);
        }
    }
}
