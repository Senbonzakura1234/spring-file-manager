package com.app.manager.model;

import java.util.Date;

import static java.lang.Math.abs;

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

    public static long roundUpIntDiv(long num, long divisor) {
        int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
        return sign * (abs(num) + abs(divisor) - 1) / abs(divisor);
    }

}
