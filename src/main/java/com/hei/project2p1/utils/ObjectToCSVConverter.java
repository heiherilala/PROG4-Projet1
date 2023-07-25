package com.hei.project2p1.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ObjectToCSVConverter {

    public static String convertToCSV(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);

        StringBuilder csvData = new StringBuilder();

        // Append headers
        fieldList.forEach(field -> csvData.append(field.getName()).append(","));

        csvData.deleteCharAt(csvData.length() - 1);
        csvData.append("\n");

        // Append values
        fieldList.forEach(field -> {
            field.setAccessible(true);
            try {
                csvData.append(field.get(object)).append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        csvData.deleteCharAt(csvData.length() - 1);

        return csvData.toString();
    }
}