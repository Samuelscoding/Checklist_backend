package com.example.api;

import com.example.db.ChecklistDAO;
import com.example.db.ChecklistItem;

//import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;

public class ChecklistController {

    private final ChecklistDAO checklistDAO = new ChecklistDAO();

    public Handler getChecklistItems = ctx -> {

        List<ChecklistItem> checklistItems = checklistDAO.getChecklistItems();
        ctx.json(checklistItems);
    };

    public Handler addItemToChecklist = ctx -> {
        
        ChecklistItem newItem = ctx.bodyAsClass(ChecklistItem.class);
        boolean isPreliminary = ctx.formParamAsClass("isPreliminary", Boolean.class).getOrDefault(false);
        boolean isRelease = ctx.formParamAsClass("isRelease", Boolean.class).getOrDefault(false);
        checklistDAO.addItemToChecklist(newItem, isPreliminary, isRelease);
        ctx.status(201).json(newItem);
    };
    
}
