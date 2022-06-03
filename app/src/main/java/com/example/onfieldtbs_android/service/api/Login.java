package com.example.onfieldtbs_android.service.api;

import android.util.Log;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Login {

    private static Login instance;
    private static String auth;
    private final String username;
    private final String password;

    private Login(String username, String password, String auth) {
        this.username = username;
        this.password = password;
        Login.auth = auth;
    }

    public synchronized static void createLogin(String username, String password, String auth){
        if(instance == null || !instance.isSameLogin(username, password, auth)){
            instance = new Login(username, password, auth);
        }
    }

    public static Login getInstance() { return instance; }

    public String getUsername() {
        return username;
    }

    public static String getAuth() {
        return auth;
    }

    public static Login initInstance(String  username, String password, String auth){
        if (instance == null || !instance.isSameLogin(username, password, auth)) {
            createLogin(username,password, auth);
        }
        return instance;
    }

    public static Login initInstanceAuth(String auth){
        String username = getCredentials(auth).split(":")[0];
        String password = getCredentials(auth).split(":")[1];
        if (instance == null || !instance.isSameLogin(username, password, auth)) {
            createLogin(username, password, auth);
        }
        return instance;
    }

    private static String getCredentials(String auth) {
        String base64Credentials = auth.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        return new String(credDecoded, StandardCharsets.UTF_8);
    }

    public boolean isSameLogin(String username, String password, String auth){
        return this.username.equals(username) && this.password.equals(password) && this.auth.equals(auth);
    }
}
