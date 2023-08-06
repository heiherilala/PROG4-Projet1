package com.hei.project2p1.utils;

import com.hei.project2p1.exception.InternalServerException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ObjectToCSVConverter {

    public static String convertToCSV(Object object, boolean includeHeader) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);

        StringBuilder csvData = new StringBuilder();

        if (includeHeader) {
            // Append headers only if 'includeHeader' is true
            fieldList.forEach(field -> csvData.append(field.getName()).append(","));
            csvData.deleteCharAt(csvData.length() - 1);
            csvData.append("\n");
        }

        // Append values
        fieldList.forEach(field -> {
            field.setAccessible(true);
            try {
                csvData.append(field.get(object)).append(",");
            } catch (IllegalAccessException e) {
                throw new InternalServerException(e.getMessage());
            }
        });

        csvData.deleteCharAt(csvData.length() - 1);
        return csvData.toString();
    }

    public static <T> String convertToCSV(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty.");
        }

        StringBuilder csvData = new StringBuilder();

        // Include header only once
        csvData.append(convertToCSV(list.get(0), true)).append("\n");

        if (list.size()>1){
            list.subList(1, list.size()).forEach(object -> {
                String row = convertToCSV(object, false);
                csvData.append(row).append("\n");
            });
        }

        // Append values for each object in the list


        return csvData.toString();
    }
}