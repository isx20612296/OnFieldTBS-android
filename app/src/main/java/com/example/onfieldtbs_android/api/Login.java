package com.example.onfieldtbs_android.api;

import android.util.Base64;

public class Login {

    private static Login instance;
    private String username;
    private String password;
    private static String auth;

    private Login(String username, String password, String auth) {
        this.username = username;
        this.password = password;
        this.auth = auth;
    }



    public synchronized static void createLogin(String username, String password, String auth){
        if(instance == null){
            instance = new Login(username, password, auth);
        }
    }

    private String getUsername() {
        return username;
    }

    private static String getAuth() {
        return auth;
    }


    public static Login getInstance(String  username, String password, String auth){
        if (instance == null) createLogin(username,password, auth);
        return instance;
    }
}
