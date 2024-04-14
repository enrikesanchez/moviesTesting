package com.movies.catalog.service;

import com.movies.catalog.entity.Movie;
import com.movies.catalog.enums.ParentalGuide;
import com.movies.catalog.exception.MovieAlreadyExistsException;
import com.movies.catalog.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class MovieServiceUnitTest {
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    MovieService movieService;

    @Test
    void testFindAll() {
        // GIVEN
        Movie movie1 = new Movie(1, "Star Wars: Episode IV - A New Hope", LocalDate.parse("1977-05-25"), ParentalGuide.PG, "2h 1m");
        Movie movie2 = new Movie(2,"Star Wars: Episode V - The Empire Strikes Back", LocalDate.parse("1980-05-17"), ParentalGuide.PG, "2h 4m");
        given(movieRepository.findAll()).willReturn(List.of(movie1, movie2));

        // WHEN
        List<Movie> actualMovies = movieService.findAll();

        // THEN
        Long expectedMoviesCount = 2L;
        assertEquals(expectedMoviesCount, actualMovies.size());
        assertEquals(movie1, actualMovies.get(0));
        assertEquals(movie2, actualMovies.get(1));
    }

    @Test
    void testFindById() {
        // GIVEN
        Movie movie1 = new Movie(1, "Star Wars: Episode IV - A New Hope", LocalDate.parse("1977-05-25"), ParentalGuide.PG, "2h 1m");
        given(movieRepository.findById(anyLong())).willReturn(Optional.of(movie1));

        // WHEN
        Optional<Movie> actualMovie = movieService.findById(1L);

        // THEN
        assertEquals(movie1, actualMovie.get());
    }

    @Test
    void testAddNew_Existing() {
        // GIVEN
        Movie movie1 = new Movie(0, "Star Wars: Episode IV - A New Hope", LocalDate.parse("1977-05-25"), ParentalGuide.PG, "2h 1m");
        Movie movie2 = new Movie(1, "Star Wars: Episode IV - A New Hope", LocalDate.parse("1977-05-25"), ParentalGuide.PG, "2h 1m");
        given(movieRepository.findByTitleAndReleaseDate(anyString(), any(LocalDate.class))).willReturn(Optional.of(movie2));
        given(movieRepository.save(any())).willReturn(movie2);

        // WHEN
        Exception exception = assertThrows(MovieAlreadyExistsException.class, () -> {
            Movie actualMovie = movieService.addNew(movie1);
        });

        // THEN
        assertNotNull(exception);
    }

    @Test
    void testAddNew_NotExisting() {
        // GIVEN
        Movie movie1= new Movie(0, "Star Wars: Episode IV - A New Hope", LocalDate.parse("1977-05-25"), ParentalGuide.PG, "2h 1m");
        Movie movie2= new Movie(1, "Star Wars: Episode IV - A New Hope", LocalDate.parse("1977-05-25"), ParentalGuide.PG, "2h 1m");
        given(movieRepository.save(any())).willReturn(movie2);
        given(movieRepository.findByTitleAndReleaseDate(anyString(), any(LocalDate.class))).willReturn(Optional.empty());

        // WHEN
        Movie actualMovie = movieService.addNew(movie1);

        // THEN
        assertEquals(movie2, actualMovie);
    }
}
