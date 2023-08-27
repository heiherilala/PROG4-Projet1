package com.hei.project2p1.controller.mapper.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

@Component
public class ConvertInputTypeToDomain {
    private ConvertInputTypeToDomain(){
    }
    public static LocalDate stringInputValueToLocalDate(String input){
        if (input==null) {
            return null;
        }
        return !input.isEmpty() ?LocalDate.parse(input):null;
    }

    public static String multipartImageToString(MultipartFile multipartFile) {
        String result;
        try {
            result = Base64.getEncoder().encodeToString(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String stringInputOfString(String input){
        if (input==null){
            return null;
        }
        return !input.isEmpty() ?input:null;
    }

    public static Integer stringInputOfInteger(String input){
        if (input==null){
            return null;
        }
        return !input.isEmpty() ? Integer.valueOf(input) : null;
    }

}
