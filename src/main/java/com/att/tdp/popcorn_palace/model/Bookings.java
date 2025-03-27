package com.att.tdp.popcorn_palace.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bookings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"showtimeId", "seatNumber"})
})
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bookingId")
    private UUID bookingId; //x
    @Column(name = "showtimeId")
    private Long showtimeId; //
    @Column(name = "seatNumber")
    private int seatNumber;

    @Column(name = "userId")
    private UUID userId; //

    public Bookings() {}

    public Bookings(int seatNumber, UUID userId, Long showtimeId) {
        this.seatNumber = seatNumber;
        this.userId = userId;
        this.showtimeId = showtimeId;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
