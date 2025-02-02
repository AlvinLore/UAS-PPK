package com.example.pendaftaranukm.model;

public class User {
    private Long id;
    private String nim;
    private String name;
    private String userClass;
    private String email;
    private String password;

    public User() {

    }

    public User(String nim, String name, String userClass, String email) {
        this.nim = nim;
        this.name = name;
        this.userClass = userClass;
        this.email = email;
    }

    public User(String nim, String name, String userClass, String email, String password) {
        this.nim = nim;
        this.name = name;
        this.userClass = userClass;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String nim, String name, String userClass, String email, String password) {
        this.id = id;
        this.nim = nim;
        this.name = name;
        this.userClass = userClass;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
