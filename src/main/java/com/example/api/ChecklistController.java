package com.example.api;

import com.example.db.ChecklistDAO;
import com.example.db.ChecklistItem;
import com.example.db.Version;

import io.javalin.http.Handler;

import java.util.List;

public class ChecklistController {

    private final ChecklistDAO checklistDAO = new ChecklistDAO();

    // Handler um Aufgaben zu importieren
    public Handler importChecklistItems = ctx -> {
        try {
            ImportChecklistRequest importRequest = ctx.bodyAsClass(ImportChecklistRequest.class);
            System.out.println("Received import request for version: " + importRequest.getVersion());
            System.out.println("Number of checklist items: " + importRequest.getChecklistItems().size());
            System.out.println("Imported Data: " + ctx.body());

            String version = ctx.bodyAsClass(ImportChecklistRequest.class).getVersion();
            List<ChecklistItem> importedItems = ctx.bodyAsClass(ImportChecklistRequest.class).getChecklistItems();
            checklistDAO.replaceChecklistItems(version, importedItems);
            ctx.status(200).json(importedItems);
        } catch(Exception e) {
            e.printStackTrace();
            ctx.status(500).json("Error importing checklist items");
        }

    };

    public static class ImportChecklistRequest {
        private String version;
        private List<ChecklistItem> checklistItems;

        public String getVersion() {
            return version;
        }

        public List<ChecklistItem> getChecklistItems() {
            return checklistItems;
        }
    }

    // Handler um Version abzurufen
    public Handler getVersions = ctx -> {
        List<Version> versions = checklistDAO.getVersions();
        ctx.json(versions);
    };

    // Handler um neue Version hinzuzufügen
    public Handler addVersion = ctx -> {
        Version newVersion = ctx.bodyAsClass(Version.class);
        checklistDAO.addVersion(newVersion);
        ctx.status(201).json(newVersion);
    };

    public Handler getChecklistItems = ctx -> {

        List<ChecklistItem> checklistItems;

        String departmentFilter = ctx.queryParam("department");
        String versionFilter = ctx.queryParam("version");
        boolean showIncompleteTasks = ctx.queryParam("showIncompleteTasks") != null;


        if(versionFilter != null && !versionFilter.isEmpty()) {
            if(showIncompleteTasks) {
                if (departmentFilter != null && !departmentFilter.isEmpty()) {
                    checklistItems = checklistDAO.getIncompleteChecklistItemsByDepartmentAndVersion(departmentFilter, versionFilter);
                } else {
                    checklistItems = checklistDAO.getIncompleteChecklistItemsByVersion(versionFilter);
                }
            } else {
                if (departmentFilter != null && !departmentFilter.isEmpty()) {
                    checklistItems = checklistDAO.getChecklistItemsByDepartmentAndVersion(departmentFilter, versionFilter);
                } else {
                    checklistItems = checklistDAO.getChecklistItemsByVersion(versionFilter);
                }
            }
        } else if(departmentFilter != null && !departmentFilter.isEmpty()) {
            if(showIncompleteTasks) {
                checklistItems = checklistDAO.getIncompleteChecklistItemsByDepartment(departmentFilter);
            } else {
                checklistItems = checklistDAO.getChecklistItemsByDepartment(departmentFilter);
            }
        } else {
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