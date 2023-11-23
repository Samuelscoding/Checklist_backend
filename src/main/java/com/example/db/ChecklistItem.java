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
    private final String colorClass_pv;
    private final String colorClass_rv;

    public ChecklistItem(){
        this.id = 0;
        this.number = 0;
        this.task = "";
        this.department = "";
        this.person = "";
        this.plannedDate = null;
        this.completedDate = null;
        this.signature = "";
        this.colorClass_pv = "";
        this.colorClass_rv = "";
    }

    public ChecklistItem(int id, float number, String task, String department, String person, LocalDate plannedDate, LocalDate completedDate, String signature, String colorClass_pv, String colorClass_rv){
        this.id = id;
        this.number = number;
        this.task = task;
        this.department = department;
        this.person = person;
        this.plannedDate = plannedDate;
        this.completedDate = completedDate;
        this.signature = signature;
        this.colorClass_pv = colorClass_pv;
        this.colorClass_rv = colorClass_rv;
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

    public String getFormattedPlannedDate(){
        if(plannedDate != null){
            return plannedDate.toString();
        } else {
            return "";
        }
    }

    public String getFormattedCompletedDate(){
        if(completedDate != null) {
            return completedDate.toString();
        } else {
            return "";
        }
    }

    public String getColorClass_pv(){
        return colorClass_pv;
    }

    public String getColorClass_rv(){
        return colorClass_rv;
    }

    public void setId(int generateId) {
    }
}
