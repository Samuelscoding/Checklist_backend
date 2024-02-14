package com.example;

import com.example.api.ChecklistController;

import io.javalin.Javalin;

public class App 
{
    public static void main( String[] args ) {
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                    it.allowCredentials = true;
                    it.allowedOrigins();
                });
            });
        }).start(5500);
        
        ChecklistController checklistController = new ChecklistController();
        app.get("/api/checklist/admin", checklistController.getChecklistItemsForAdmin);
        app.get("/api/checklist/user", checklistController.getChecklistItemsforUser);

        app.post("/api/checklist/addTask", checklistController.addItemToChecklist);
        app.delete("/api/checklist/delete/{taskId}", checklistController.deleteItemFromChecklist);
        app.put("/api/checklist/edit/{taskId}", checklistController.updateItemInChecklist);
        app.post("/api/checklist/sendReminderEmail", checklistController.sendReminderEmail);

        app.post("/api/checklist/import", checklistController.importChecklistItems);

        app.get("/api/versions", checklistController.getVersions);
        app.post("api/version/addVersion", checklistController.addVersion);
        app.put("/api/version/editVersion/{id}", checklistController.editVersion);
        app.delete("/api/version/deleteVersion/{versionName}", checklistController.deleteVersion);
        app.post("/api/version/completeVersion/{id}", checklistController.completeVersion);

        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
            ctx.status(500).json("Internal Server Error");
        });

    }
}
