package com.att.tdp.popcorn_palace.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "theater")
public class Theater {
    @Id
    @Column(name = "title")
    private String title;
    @Column(name = "total_seats")
    private int total_seats;

    public Theater() {}
    public Theater(String title, int total_seats) {
        this.title = title;
        this.total_seats = total_seats;
    }
    public int getTotalSeats() {
        return total_seats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.total_seats = totalSeats;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
