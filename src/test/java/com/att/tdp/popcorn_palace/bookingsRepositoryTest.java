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
public class bookingsRepositoryTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void boolTicket() throws Exception {

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
                + "\"startTime\": \"2025-06-25T11:47:46.125405Z\","
                + "\"endTime\": \"2025-06-25T14:47:46.125405Z\""
                + "}";
        this.mockMvc.perform(post("/showtimes")
                        .contentType("application/json")
                        .content(showtimesJson));
        String bookingJson = "{"
                + "\"showtimeId\": 1,"
                + "\"seatNumber\": 15,"
                + "\"userId\": \"84438967-f68f-4fa0-b620-0f08217e76af\""
                + "}";
        this.mockMvc.perform(post("/bookings")
                    .contentType("application/json")
                    .content(bookingJson))
                    .andDo(print())
                    .andExpect(status().isOk());

        String bookingJson2 = "{"
                + "\"showtimeId\": 2,"
                + "\"seatNumber\": 15,"
                + "\"userId\": \"84438967-f68f-4fa0-b620-0f08217e76af\""
                + "}";
        this.mockMvc.perform(post("/bookings")
                        .contentType("application/json")
                        .content(bookingJson2))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Showtime not exist with id :2"));


        String bookingJson3 = "{"
                + "\"showtimeId\": 1,"
                + "\"seatNumber\": -15,"
                + "\"userId\": \"84438967-f68f-4fa0-b620-0f08217e76af\""
                + "}";
        this.mockMvc.perform(post("/bookings")
                        .contentType("application/json")
                        .content(bookingJson3))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("invalid seat number"));



    }
}
