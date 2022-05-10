package com.example.onfieldtbs_android.models;

import java.util.Set;
import java.util.UUID;

public class Level {

    private UUID id;
    private String name;
    private String description;

    private Set<Technician> technicians;

    public Level() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Technician> getTechnicians() {
        return technicians;
    }

    public void setTechnicians(Set<Technician> technicians) {
        this.technicians = technicians;
    }
}
