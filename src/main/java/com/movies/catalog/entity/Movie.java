package com.movies.catalog.entity;

import com.movies.catalog.enums.ParentalGuide;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Movie {
    @Schema(description = "The id")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Movie title")
    @NotNull
    private String title;

    @Schema(description = "Movie Release Date")
    @NotNull
    private LocalDate releaseDate;

    @Schema(description = "Movie Parental Guide. Possible values are: G, PG, PG_13, R, NC_17, NR")
    @NotNull
    private ParentalGuide parentalGuide;

    @Schema(description = "Movie duration")
    @NotNull
    private String runtime;

    public Movie() {
    }

    public Movie(long id, String title, LocalDate releaseDate, ParentalGuide parentalGuide, String runtime) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.parentalGuide = parentalGuide;
        this.runtime = runtime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ParentalGuide getParentalGuide() {
        return parentalGuide;
    }

    public void setParentalGuide(ParentalGuide parentalGuide) {
        this.parentalGuide = parentalGuide;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }
}
