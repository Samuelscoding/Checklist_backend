package com.example.db;

import java.time.LocalDate;

public class Version {

    private int id;
    private String name;
    private LocalDate preliminaryrelease;
    private LocalDate finalrelease;

    public Version() {
        this.id = 0;
        this.name = "";
        this.preliminaryrelease = null;
        this.finalrelease = null;
    }

    public Version(int id, String name, LocalDate preliminaryrelease, LocalDate finalrelease) {
        this.id = id;
        this.name = name;
        this.preliminaryrelease = preliminaryrelease;
        this.finalrelease = finalrelease;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public LocalDate getPreliminaryrelease(){
        return this.preliminaryrelease;
    }

    public LocalDate getfinalrelease(){
        return this.finalrelease;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
