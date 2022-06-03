package com.example.onfieldtbs_android.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String PREFERENCES_FILE = "onfield_preferences";

    public static String formatDate(String date){
        return LocalDateTime.parse(date).format(DateTimeFormatter.ofPattern("dd MMM"));
    }

    public static String formatDateTime(String date){
        return LocalDateTime.parse(date).format(DateTimeFormatter.ofPattern("dd MMM HH:mm"));
    }
}
