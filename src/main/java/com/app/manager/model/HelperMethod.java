package com.app.manager.model;

import java.time.Instant;
import java.util.Date;

public class HelperMethod {
    public static String getDateString(Long timeStamp){
        try {
            return (new Date(timeStamp)).toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return "";
        }
    }
}
