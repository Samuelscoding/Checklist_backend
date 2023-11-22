package com.example.db;

import java.time.LocalDate;

public class ChecklistItem {

    private final int id;
    private final float number;
    private final String task;
    private final String department;
    private final String person;
    private final LocalDate plannedDate;
    private final LocalDate completedDate;
    private final String signature;

    public ChecklistItem(int id, float number, String task, String department, String person, LocalDate plannedDate, LocalDate completedDate, String signature){
        this.id = id;
        this.number = number;
        this.task = task;
        this.department = department;
        this.person = person;
        this.plannedDate = plannedDate;
        this.completedDate = completedDate;
        this.signature = signature;
    }
    public int getId(){
        return this.id;
    }

    public float getNumber(){
        return this.number;
    }

    public String getTask(){
        return this.task;
    }

    public String getDepartment(){
        return this.department;
    }

    public String getPerson(){
        return this.person;
    }

    public LocalDate getPlannedDate(){
        return this.plannedDate;
    }

    public LocalDate getCompletedDate(){
        return this.completedDate;
    }

    public String getSignature(){
        return this.signature;
    }

}
