package com.hei.project2p1.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Component
public class PhoneFormating {
    public static String reformatPhoneNumber(String phone) {
        // Define the regex pattern
        Pattern pattern = Pattern.compile("^(\\+\\d+),(\\d{10})$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            String countryCode = matcher.group(1);
            String phoneNumber = matcher.group(2);

            // Reformat phoneNumber using regex
            String reformattedPhoneNumber = phoneNumber.replaceAll("(\\d{3})(\\d{2})(\\d{3})(\\d{2})", "$1 $2 $3 $4");

            return countryCode + " " + reformattedPhoneNumber;
        } else {
            return phone; // Return the original phone if format is incorrect
        }
    }
}
