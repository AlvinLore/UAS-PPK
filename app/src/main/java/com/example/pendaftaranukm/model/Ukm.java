package com.example.pendaftaranukm.model;

public class Ukm {
    private Long id;
    private String name;
    private Leader leader;
    private String description;

    public Ukm() {

    }

    public Ukm(Long id, String name, Leader leader, String description) {
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.description = description;
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

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
