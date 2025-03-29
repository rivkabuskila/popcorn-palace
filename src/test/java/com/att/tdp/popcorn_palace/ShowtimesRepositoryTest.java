package com.att.tdp.popcorn_palace;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShowtimesRepositoryTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void addShowtimes() throws Exception {
        String movieJson = "{"
                + "\"title\": \"Sample Movie Title\","
                + "\"genre\": \"Action\","
                + "\"duration\": 120,"
                + "\"rating\": 8.7,"
                + "\"releaseYear\": 2025"
                + "}";
        this.mockMvc.perform(post("/movies")
                        .contentType("application/json")
                        .content(movieJson));

        String showtimesJson = "{"
                + "\"movieId\": 2,"
                + "\"price\": 20.2,"
                + "\"theater\": \"Sample Theater\","
                + "\"startTime\": \"2025-07-25T11:47:46.125405Z\","
                + "\"endTime\": \"2025-07-25T14:47:46.125405Z\""
                + "}";
        this.mockMvc.perform(post("/showtimes")
                        .contentType("application/json")
                        .content(showtimesJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"price\":20.2,\"movieId\":2,\"theater\":\"Sample Theater\",\"startTime\":\"2025-07-25T11:47:46.125405Z\",\"endTime\":\"2025-07-25T14:47:46.125405Z\"}")));

        String movieJson2 = "{"
                + "\"title\": \"Sample Movie Title 1\","
                + "\"genre\": \"Action\","
                + "\"duration\": 150,"
                + "\"rating\": 5.7,"
                + "\"releaseYear\": 2024"
                + "}";
        this.mockMvc.perform(post("/movies")
                .contentType("application/json")
                .content(movieJson2));


        String showtimesJson2 = "{"
                + "\"movieId\": 3,"
                + "\"price\": 20.2,"
                + "\"theater\": \"Sample Theater\","
                + "\"startTime\": \"2025-07-25T11:47:46.125405Z\","
                + "\"endTime\": \"2025-07-25T14:47:46.125405Z\""
                + "}";
        this.mockMvc.perform(post("/showtimes")
                        .contentType("application/json")
                        .content(showtimesJson2))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("There is already a showtime scheduled for this theater at the given time"));
    }

    @Test
    void getShowtimes() throws Exception {
        String movieJson = "{"
                + "\"title\": \"Sample Movie Title\","
                + "\"genre\": \"Action\","
                + "\"duration\": 120,"
                + "\"rating\": 8.7,"
                + "\"releaseYear\": 2025"
                + "}";
        this.mockMvc.perform(post("/movies")
                .contentType("application/json")
                .content(movieJson));

        String showtimesJson = "{"
                + "\"movieId\": 3,"
                + "\"price\": 20.2,"
                + "\"theater\": \"Sample Theater 1\","
                + "\"startTime\": \"2025-07-25T11:47:46.125405Z\","
                + "\"endTime\": \"2025-07-25T14:47:46.125405Z\""
                + "}";
        this.mockMvc.perform(post("/showtimes")
                        .contentType("application/json")
                        .content(showtimesJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":2,\"price\":20.2,\"movieId\":3,\"theater\":\"Sample Theater 1\",\"startTime\":\"2025-07-25T11:47:46.125405Z\",\"endTime\":\"2025-07-25T14:47:46.125405Z\"}")));

        this.mockMvc.perform(get("/showtimes/2")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":2,\"price\":20.2,\"movieId\":3,\"theater\":\"Sample Theater 1\",\"startTime\":\"2025-07-25T11:47:46.125405Z\",\"endTime\":\"2025-07-25T14:47:46.125405Z\"}")));
        this.mockMvc.perform(get("/showtimes/56546465")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("showtime not exist with id :56546465"));
    }

    @Test
    void updateShowtimes() throws Exception {
        String showtimesJson = "{"
                + "\"movieId\": 1,"
                + "\"price\": 30.2,"
                + "\"theater\": \"Sample Theater 1\","
                + "\"startTime\": \"2025-07-25T11:47:46.125405Z\","
                + "\"endTime\": \"2025-07-25T14:47:46.125405Z\""
                + "}";
        this.mockMvc.perform(post("/showtimes/update/2")
                        .contentType("application/json")
                        .content(showtimesJson))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/showtimes/update/56546465")
                        .contentType("application/json")
                        .content(showtimesJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("showtime not exist with id :56546465"));


    }

    @Test
    void deleteShowtimes() throws Exception {
        String movieJson = "{"
                + "\"title\": \"Sample Movie Title\","
                + "\"genre\": \"Action\","
                + "\"duration\": 120,"
                + "\"rating\": 8.7,"
                + "\"releaseYear\": 2025"
                + "}";
        this.mockMvc.perform(post("/movies")
                .contentType("application/json")
                .content(movieJson));

        String showtimesJson = "{"
                + "\"movieId\": 1,"
                + "\"price\": 20.2,"
                + "\"theater\": \"Sample Theater\","
                + "\"startTime\": \"2025-07-25T11:47:46.125405Z\","
                + "\"endTime\": \"2025-07-25T14:47:46.125405Z\""
                + "}";
        this.mockMvc.perform(post("/showtimes")
                .contentType("application/json")
                .content(showtimesJson));
        this.mockMvc.perform(delete("/showtimes/1"))
                .andExpect(status().isOk());
        this.mockMvc.perform(delete("/showtimes/56546465"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("showtime not exist with id :56546465"));

    }


}
