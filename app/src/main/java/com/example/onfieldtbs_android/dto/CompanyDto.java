package com.example.onfieldtbs_android.dto;



import com.example.onfieldtbs_android.dto.model.Location;

import java.util.List;

public class CompanyDto {

    private String companyName;
    private String address;
    private Location location;
    private List<String> incidencesList;


    public CompanyDto() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getIncidencesList() {
        return incidencesList;
    }

    public void setIncidencesList(List<String> incidencesList) {
        this.incidencesList = incidencesList;
    }
}
