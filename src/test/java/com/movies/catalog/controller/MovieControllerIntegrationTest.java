package com.movies.catalog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllMovies() throws Exception {
        this.mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[")))
                .andExpect(content().string(containsString("]")));
    }

    @Test
    public void testGetMoviesById_NotExisting() throws Exception {
        this.mockMvc.perform(get("/movies/10"))
                .andExpect(status().isNoContent());;
    }

    @Test
    public void testGetMoviesById_Existing() throws Exception {
        this.mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"title\":\"Star Wars: Episode IV - A New Hope\",\"releaseDate\":\"1977-05-25\",\"parentalGuide\":\"PG\",\"runtime\":\"2h 1m\"}"));
    }
    @Test
    public void testPostMovies_Existing() throws Exception {
        this.mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":0,\"title\":\"Star Wars: Episode IV - A New Hope\",\"releaseDate\":\"1977-05-25\",\"parentalGuide\":\"PG\",\"runtime\":\"2h 1m\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void testPostMovies_NotExisting() throws Exception {
        this.mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 0,\"title\": \"Star Wars: Episode V - The Empire Strikes Back\",\"releaseDate\": \"1980-05-17\",\"parentalGuide\": \"PG\",\"runtime\": \"2h 4m\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 2,\"title\": \"Star Wars: Episode V - The Empire Strikes Back\",\"releaseDate\": \"1980-05-17\",\"parentalGuide\": \"PG\",\"runtime\": \"2h 4m\"}"));
    }

    @Test
    public void testPostMovies_MissingProperties() throws Exception {
        this.mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":0,\"title\":\"Star Wars: Episode VI - Return of the Jedi\",\"releaseDate\":\"1983-05-23\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
