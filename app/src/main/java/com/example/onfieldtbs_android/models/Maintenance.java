package com.example.onfieldtbs_android.models;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class Maintenance implements Serializable {

    private UUID id;
    private String name;
    private String description;
    private Double price;

    private Set<Company> companies;

    public Maintenance() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }
}
