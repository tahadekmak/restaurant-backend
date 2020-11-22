package cme.restaurantbackend.dto;

import cme.restaurantbackend.model.Person;
import cme.restaurantbackend.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

public class VisitBrief {

    private Long id;
    private String name;
    private LocalDateTime date;

    public VisitBrief() {

    }

    public VisitBrief(Long id, String name, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
