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

            Javalin app = Javalin.create().start(5500);
            
            Authenticator authenticator = new LDAPAuthenticator();
            
            AuthController authController = new AuthController(authenticator);
            
            app.post("/login", authController::login);

        } catch (IOException e) {

            e.printStackTrace();
        }
        

    }
}
