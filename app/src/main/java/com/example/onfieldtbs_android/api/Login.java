package com.example.onfieldtbs_android.api;

import android.util.Log;

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
        if(instance == null || !instance.isSameLogin(username, password)){
            instance = new Login(username, password, auth);
        }
    }

    public String getUsername() {
        return username;
    }

    public static String getAuth() {
        return auth;
    }


    public static Login getInstance(String  username, String password, String auth){
        if (instance == null || !instance.isSameLogin(username, password)) {
            createLogin(username,password, auth);
            Log.i("LOGINAA", "LOGIN CREATED. USER: " + username + ", PASS: " + password);
        }
        return instance;
    }

    public boolean isSameLogin(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }
}
