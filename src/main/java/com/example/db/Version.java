package com.example.db;

import java.time.LocalDate;

public class Version {

    private int id;
    private String name;
    private LocalDate preliminaryrelease;
    private LocalDate finalrelease;
    private LocalDate finishedDate;
    private String signature;
    private boolean released;

    public Version() {
        this.id = 0;
        this.name = "";
        this.preliminaryrelease = null;
        this.finalrelease = null;
        this.finishedDate = null;
        this.signature = "";
        this.released = false;
    }

    public Version(int id, String name, LocalDate preliminaryrelease, LocalDate finalrelease, LocalDate finishedDate, String signature, boolean released) {
        this.id = id;
        this.name = name;
        this.preliminaryrelease = preliminaryrelease;
        this.finalrelease = finalrelease;
        this.finishedDate = finishedDate;
        this.signature = signature;
        this.released = released;
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

    public LocalDate getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(LocalDate finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }
    
}
