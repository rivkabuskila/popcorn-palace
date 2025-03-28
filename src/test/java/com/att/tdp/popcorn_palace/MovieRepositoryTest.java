package com.att.tdp.popcorn_palace;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MovieRepositoryTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void addMovie() throws Exception{
        String movieJson = "{"
                + "\"title\": \"Sample Movie Title\","
                + "\"genre\": \"Action\","
                + "\"duration\": 120,"
                + "\"rating\": 8.7,"
                + "\"releaseYear\": 2025"
                + "}";
        this.mockMvc.perform(post("/movies")
                        .contentType("application/json")
                        .content(movieJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"title\":\"Sample Movie Title\",\"genre\":\"Action\",\"duration\":120,\"rating\":8.7,\"releaseYear\":2025}")));

        this.mockMvc.perform(get("/movies/all")
                        .contentType("application/json")
                        .content(movieJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":1,\"title\":\"Sample Movie Title\",\"genre\":\"Action\",\"duration\":120,\"rating\":8.7,\"releaseYear\":2025}]")));

        String movieJson2 = "{"
                + "\"title\": \"\","
                + "\"genre\": \"Action\","
                + "\"duration\": 120,"
                + "\"rating\": 8.7,"
                + "\"releaseYear\": 2025"
                + "}";
        this.mockMvc.perform(post("/movies")
                        .contentType("application/json")
                        .content(movieJson2))
                .andDo(print())
                .andExpect(status().isBadRequest())  
                .andExpect(jsonPath("$.message").value("Title is required and cannot be empty"));

        this.mockMvc.perform(get("/movies/all")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":1,\"title\":\"Sample Movie Title\",\"genre\":\"Action\",\"duration\":120,\"rating\":8.7,\"releaseYear\":2025}]")));

        String movieJson3 = "{"
                + "\"title\": \"Sample Movie Title\","
                + "\"genre\": \"Action\","
                + "\"duration\": 120,"
                + "\"rating\": -8.7,"
                + "\"releaseYear\": 2025"
                + "}";
        this.mockMvc.perform(post("/movies")
                        .contentType("application/json")
                        .content(movieJson3))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Rating must be between 0 to 10"));

    }
    @Test
    void updateMovie() throws Exception {
        String movieJson = "{"
                + "\"title\": \"Title2\","
                + "\"genre\": \"Action\","
                + "\"duration\": 120,"
                + "\"rating\": 8.7,"
                + "\"releaseYear\": 2025"
                + "}";
        this.mockMvc.perform(post("/movies")
                        .contentType("application/json")
                        .content(movieJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":2,\"title\":\"Title2\",\"genre\":\"Action\",\"duration\":120,\"rating\":8.7,\"releaseYear\":2025}")));
        this.mockMvc.perform(get("/movies/all")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":2,\"title\":\"Title2\",\"genre\":\"Action\",\"duration\":120,\"rating\":8.7,\"releaseYear\":2025}]")));

        String movieJson2 = "{"
                + "\"title\": \"Title2\","
                + "\"genre\": \"Action\","
                + "\"duration\": 120,"
                + "\"rating\": 7,"
                + "\"releaseYear\": 2025"
                + "}";
        this.mockMvc.perform(post("/movies/update/Title2")
                        .contentType("application/json")
                        .content(movieJson2))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/movies/update/Title3")
                        .contentType("application/json")
                        .content(movieJson2))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("movie not exist with title :Title3")));
        this.mockMvc.perform(get("/movies/all")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":2,\"title\":\"Title2\",\"genre\":\"Action\",\"duration\":120,\"rating\":7.0,\"releaseYear\":2025}]")));

    }
    @Test
    void deleteMovie() throws Exception {
        this.mockMvc.perform(delete("/movies/Sample Movie Title"))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/movies/all")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));

        this.mockMvc.perform(delete("/movies/Title3")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("movie not exist with title :Title3")));
    }


}

