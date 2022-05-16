package com.example.onfieldtbs_android.models;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class Technician {
    private UUID id;
    private String name;
    private String lastname;
    private String email;
    private boolean license;
    private String phone;
    private String createdAt;

    private User user;
    private Level level;
    private Set<Incidence> incidences;
    private Set<Comment> comments;

    public Technician() {
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLicense() {
        return license;
    }

    public void setLicense(boolean license) {
        this.license = license;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Set<Incidence> getIncidences() {
        return incidences;
    }

    public void setIncidences(Set<Incidence> incidences) {
        this.incidences = incidences;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
