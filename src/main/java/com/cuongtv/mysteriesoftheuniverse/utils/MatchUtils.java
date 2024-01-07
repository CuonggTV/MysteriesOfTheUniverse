package com.cuongtv.mysteriesoftheuniverse.utils;

import java.time.LocalDate;
import java.time.Period;

public class MatchUtils {
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
