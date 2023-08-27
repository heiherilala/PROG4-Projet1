package com.hei.project2p1.controller.mapper.utils;

import com.hei.project2p1.model.Employee;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

@Component
public class ConvertNullValueToView {
    public ConvertNullValueToView() {
    }

    public static String valueToView(Integer value){
        if (value==null){
            return null;
        }
        return String.valueOf(value);
    }

    public static String valueToView(LocalDate value){
        if (value==null){
            return null;
        }
        return String.valueOf(value);
    }
    public static String valueToView(Employee.SocioProfessionalCategory value){
        if (value==null){
            return null;
        }
        return String.valueOf(value);
    }

    public static String valueToView(Employee.Gender value){
        if (value==null){
            return null;
        }
        return String.valueOf(value);
    }

    public static void replaceNullFields(Object obj, Object nullReplacement)  {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    if (field.getType() == List.class) {
                        field.set(obj, List.of());
                    } else if (field.getType() == Integer.class) {
                        field.set(obj, null);
                    } else {
                        field.set(obj, nullReplacement);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
