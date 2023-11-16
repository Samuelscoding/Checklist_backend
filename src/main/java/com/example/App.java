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
                        it.allowHost("http://localhost:3000/");
                    });
                });
            }).start(5500);
            
            Authenticator authenticator = new LDAPAuthenticator();
            
            AuthController authController = new AuthController(authenticator);
            
            app.post("/login", authController::login);

            app.exception(Exception.class, (e, ctx) -> {
                e.printStackTrace();
                ctx.status(500).json("Internal Server Error");
            });

        } catch (IOException e) {

            e.printStackTrace();
        }
        

    }
}
