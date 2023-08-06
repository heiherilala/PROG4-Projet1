package com.hei.project2p1.utils;

import com.hei.project2p1.exception.InternalServerException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ObjectToCSVConverter {

    public static String convertToCSV(Object object, List<String> toSkip, boolean includeHeader) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);

        StringBuilder csvData = new StringBuilder();

        if (includeHeader) {
            // Append headers only if 'includeHeader' is true
            fieldList
                    .stream().filter(field -> !toSkip.contains(field.getName()))
                    .forEach(field -> csvData.append(field.getName()).append(","));
            csvData.deleteCharAt(csvData.length() - 1);
            csvData.append("\n");
        }

        // Append values
        fieldList
                .stream().filter(field -> !toSkip.contains(field.getName()))
                .forEach(field -> {
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

    public static <T> String convertToCSV(List<T> list, List<String> toSkip) {
        if (list == null) {
            throw new IllegalArgumentException("List should not be null");
        }

        StringBuilder csvData = new StringBuilder();
        if (!list.isEmpty()) {
            csvData.append(convertToCSV(list.get(0), toSkip, true)).append("\n");
            if (list.size()>1){
                list.subList(1, list.size()).forEach(object -> {
                    String row = convertToCSV(object, toSkip,false);
                    csvData.append(row).append("\n");
                });
            }
        }else {

        }

        return csvData.toString();
    }
}