package com.cuongtv.mysteriesoftheuniverse.utils;

import java.time.LocalDate;

public class MatchUtils {
    public static boolean matchUsername(String username){
        if (username.contains(" ")) return false;
        return true;
    }
    public static boolean matchEmail(String email){
        return email.matches("[A-Za-z0-9]+@gmail.com");
    }
    public static boolean matchPhoneNumber(String phoneNumber){
        return phoneNumber.matches("^[0-9]{10}$");
    }
    public static boolean matchBirthDay(String day){
        String today = String.valueOf(LocalDate.now());
        if (today.compareTo(day) > 0){
            return true;
        }
        return false;
    }
}
