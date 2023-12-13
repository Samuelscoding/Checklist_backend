package com.example;

import java.io.IOException;

import com.example.api.AuthController;
import com.example.api.ChecklistController;
import com.example.auth.Authenticator;
import com.example.auth.LDAPAuthenticator;


import io.javalin.Javalin;


public class App 
{
    public static void main( String[] args ) {

        try {

            Javalin app = Javalin.create(config -> {
                config.plugins.enableCors(cors -> {
                    cors.add(it -> {
                        it.anyHost();
                        it.allowCredentials = true;
                        it.allowedOrigins();
                    });
                });
            }).start(5500);
            
            Authenticator authenticator = new LDAPAuthenticator();
            
            AuthController authController = new AuthController(authenticator);
/*
            // CORS-Header manuell für alle Anfragen setzen
            app.before(ctx -> {
                ctx.header("Access-Control-Allow-Origin", ctx.header("Origin"));
                ctx.header("Access-Control-Allow-Credentials", "true");
                ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            });

            // Handle Preflight-Anfragen
            app.options("/*", ctx -> {
                ctx.status(200).json("");
            });
*/
            app.post("/login", ctx -> {
                System.out.println("Received login request");
                authController.login(ctx);
            });

            app.post("/logout", ctx -> {
                System.out.println("Received logout request");
                authController.logout(ctx);
            });
            
            ChecklistController checklistController  = new ChecklistController();
            app.get("/api/checklist", checklistController.getChecklistItems);
            app.post("/api/checklist/addTask", checklistController.addItemToChecklist);
            app.delete("/api/checklist/delete/{taskId}", checklistController.deleteItemFromChecklist);
            app.put("/api/checklist/edit/{taskId}", checklistController.updateItemInChecklist);

            app.exception(Exception.class, (e, ctx) -> {
                e.printStackTrace();
                ctx.status(500).json("Internal Server Error");
            });

        } catch (IOException e) {

            e.printStackTrace();
        }
        

    }
}
