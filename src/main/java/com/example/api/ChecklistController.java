package com.example.api;

import com.example.db.ChecklistDAO;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;

public class ChecklistController {

    private final ChecklistDAO checklistDAO = new ChecklistDAO();

    public Handler getChecklistItems = ctx -> {

        List<String> checklistItems = checklistDAO.getChecklistItems();
        ctx.json(checklistItems);
    };

    public Handler addItemToChecklist = ctx -> {
        
        String item = ctx.formParam("item");
        checklistDAO.addItemToChecklist(item);
        ctx.status(201);
    };
    
}
