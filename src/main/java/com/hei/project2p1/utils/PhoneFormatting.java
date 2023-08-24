package com.hei.project2p1.utils;

//@Component
public class PhoneFormatting {
    public static String reformatPhoneNumber(String phoneNumber) {
        String countryCode = phoneNumber.split(",")[0].trim();
        String number = phoneNumber.split(",")[1].trim();

        if (number.length() == 10) {
            return countryCode + " " + number.substring(0, 3) + " " + number.substring(3, 5) + " " + number.substring(5, 8) + " " + number.substring(8);
        } else {
            return phoneNumber; // Return as is if not a valid phone number
        }
    }
}
