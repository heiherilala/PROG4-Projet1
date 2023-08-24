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

        if (phone.getNumber() != null) {
            if (phone.getNumber().length() != 10) {
                violationMessages.add("the phone number length have to be 10, but yours is : " + phone.getNumber().length() );
            }
            if (!validateNumberRegex(phone.getNumber())) {
                violationMessages.add("Number should be alphanumeric");
            }
        }

        if (!violationMessages.isEmpty()) {
            String formattedViolationMessages = violationMessages.stream()
                    .map(String::toString)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(formattedViolationMessages);
        }

    }

    public static boolean validateNumberRegex(String number) {
        String regexPattern = "^[0-9\\s]+$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
