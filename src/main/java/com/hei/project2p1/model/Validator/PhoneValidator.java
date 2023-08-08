package com.hei.project2p1.model.Validator;

import com.hei.project2p1.exception.BadRequestException;
import com.hei.project2p1.model.Phone;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class PhoneValidator implements Consumer<Phone> {

    public  void accept(List<Phone> phones){
        phones.forEach(this::accept);
    }

    @Override
    public void accept(Phone phone) {
        Set<String> violationMessages = new HashSet<>();

        if (phone.getNumber() == null) {
            violationMessages.add("Number is mandatory");
        }
        if (!validateNumberLength(phone.getNumber())) {
            violationMessages.add("Number length should be 10");
        }

        if (!violationMessages.isEmpty()) {
            String formattedViolationMessages = violationMessages.stream()
                    .map(String::toString)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(formattedViolationMessages);
        }

    }

    public static boolean validateNumberLength(String number) {
        String regex = "^.{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
