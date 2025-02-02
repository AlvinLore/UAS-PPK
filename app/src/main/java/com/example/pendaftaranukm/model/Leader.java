package com.example.pendaftaranukm.model;

public class Leader {
    private Long id;
    private String nim;
    private String name;
    private String leaderClass;

    public Leader() {

    }

    public Leader(Long id, String nim, String name, String leaderClass) {
        this.id = id;
        this.nim = nim;
        this.name = name;
        this.leaderClass = leaderClass;
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

    public String getLeaderClass() {
        return leaderClass;
    }

    public void setLeaderClass(String leaderClass) {
        this.leaderClass = leaderClass;
    }
}
