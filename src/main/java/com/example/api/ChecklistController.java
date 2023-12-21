package com.example.api;

import com.example.db.ChecklistDAO;
import com.example.db.ChecklistItem;

import io.javalin.http.Handler;

import java.util.List;

public class ChecklistController {

    private final ChecklistDAO checklistDAO = new ChecklistDAO();

    public Handler getChecklistItems = ctx -> {

        List<ChecklistItem> checklistItems;

        String departmentFilter = ctx.queryParam("department");
        boolean showIncompleteTasks = ctx.queryParam("showIncompleteTasks") != null;

        // Überprüfen, ob ein Abteilungsfilter übergeben wurde
        if(departmentFilter != null && !departmentFilter.isEmpty()) {
            if(showIncompleteTasks) {
                checklistItems = checklistDAO.getIncompleteChecklistItemsByDepartment(departmentFilter);
            } else {
                checklistItems = checklistDAO.getChecklistItemsByDepartment(departmentFilter);
            }
        } else {
            // Wenn kein Abteilungsfilter übergeben wurde, lade volle Checklist
            if(showIncompleteTasks) {
                checklistItems = checklistDAO.getIncompleteChecklistItems();
            } else {
                checklistItems = checklistDAO.getChecklistItems();  
            }

        }
        ctx.json(checklistItems);
    };

    public Handler addItemToChecklist = ctx -> {
        
        ChecklistItem newItem = ctx.bodyAsClass(ChecklistItem.class);
        checklistDAO.addItemToChecklist(newItem);
        ctx.status(201).json(newItem);
    };

    public Handler deleteItemFromChecklist = ctx -> {

        int taskId = ctx.pathParamAsClass("taskId", Integer.class).get();
        checklistDAO.deleteItemFromChecklist(taskId);
        ctx.status(204);
    };

    public Handler updateItemInChecklist = ctx -> {

        ChecklistItem updatedItem = ctx.bodyAsClass(ChecklistItem.class);
        checklistDAO.updateItemInChecklist(updatedItem);
        ctx.status(200).json(updatedItem);
    };
    
}