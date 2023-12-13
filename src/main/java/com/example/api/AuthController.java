package com.example.api;

import com.example.auth.Authenticator;
import com.example.auth.User;
import io.javalin.http.Context;

public class AuthController {
    
    private final Authenticator authenticator;

    public AuthController(Authenticator authenticator) {

        this.authenticator = authenticator;
    }

    public void login(Context ctx) {
        
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        User user = authenticator.login(username, password);

        if(user != null) {

            ctx.sessionAttribute("currentUser", user);
            ctx.status(200).json("Login erfolgreich");
        } else {
            
            ctx.status(401).json("Login fehlgeschlagen");
        }
    }

    public void logout(Context ctx) {
        ctx.sessionAttribute("currentUser", null);
        ctx.status(200).json("Logout erfolgreich");
    }
/* 
    public static void options(Context ctx) {
        String origin = ctx.header("Origin");
        ctx.status(200).header("Access-Control-Allow-Methods", "POST, OPTIONS")
                            .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                            .header("Access-Control-Allow-Credentials", "true")
                            .header("Access-Control-Allow-Origin", origin)
                            .header("Access-Control-Max-Age", "86400")
                            .json("");
    }
*/    
}
