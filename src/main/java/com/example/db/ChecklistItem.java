package com.example.db;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChecklistItem {

    private int id;
    private final String task;
    private final String department;
    private final String person;
    private final LocalDate plannedDate;
    private final LocalDate completedDate;
    private final String signature;
    public String colorClass_pv;
    public String colorClass_rv;

    public ChecklistItem() {
        this.id = 0;
        this.task = "";
        this.department = "";
        this.person = "";
        this.plannedDate = null;
        this.completedDate = null;
        this.signature = "";
        this.colorClass_pv = "";
        this.colorClass_rv = "";
    }

    public ChecklistItem(int id, String task, String department, String person, LocalDate plannedDate, LocalDate completedDate, String signature, String colorClass_pv, String colorClass_rv){
        this.id = id;
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

    public String getColorClass_pv(){
        return this.colorClass_pv;
    }

    public String getColorClass_rv(){
        return this.colorClass_rv;
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

    public void setId(int id) {
    
        this.id = id;
    }

}
