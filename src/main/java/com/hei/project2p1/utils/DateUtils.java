package com.hei.project2p1.utils;

import java.time.LocalDate;
import java.util.Date;

public class DateUtils {
    public static String LocalDateToAge(LocalDate localDate){
        if (localDate == null) {
            return null;
        }
        LocalDate newLocalDate = LocalDate.now();
        int age = newLocalDate.getYear() - localDate.getYear();
        if (newLocalDate.getMonthValue() >=   localDate.getMonthValue()) {
            if (newLocalDate.getDayOfMonth() >= localDate.getDayOfMonth()) {
                return String.valueOf(age);
            }
        }
        age--;
        return String.valueOf(age);
    }
    public static String LocalDateToAgeByYear(LocalDate localDate){
        if (localDate == null) {
            return null;
        }
        LocalDate newLocalDate = LocalDate.now();
        int age = newLocalDate.getYear() - localDate.getYear();
        return String.valueOf(age);
    }
    public static String LocalDateToAgeWithDay(LocalDate localDate, Long day){
        if (localDate == null) {
            return null;
        }
        LocalDate anivDate = localDate.minusDays(day);
        LocalDate newLocalDate = LocalDate.now();
        int age = newLocalDate.getYear() - anivDate.getYear();
        if (newLocalDate.getMonthValue() >=   anivDate.getMonthValue()) {
            if (newLocalDate.getDayOfMonth() >= anivDate.getDayOfMonth()) {
                return String.valueOf(age);
            }
        }
        age--;
        return String.valueOf(age);
    }
}
