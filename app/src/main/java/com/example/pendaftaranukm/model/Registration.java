package com.example.pendaftaranukm.model;

import java.util.Date;

public class Registration {
    private Long id;
    private User user;
    private Ukm ukm;
    private String registrationDate;
    private String status; // PENDING, APPROVED, REJECTED

    public Registration() {

    }

    public Registration(User user, Ukm ukm, String registrationDate, String status) {
        this.user = user;
        this.ukm = ukm;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ukm getUkm() {
        return ukm;
    }

    public void setUkm(Ukm ukm) {
        this.ukm = ukm;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
