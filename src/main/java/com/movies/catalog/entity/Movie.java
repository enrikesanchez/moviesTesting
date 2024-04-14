package com.movies.catalog.entity;

import com.movies.catalog.enums.ParentalGuide;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private ParentalGuide parentalGuide;

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
