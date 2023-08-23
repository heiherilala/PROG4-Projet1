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
        if (phone.getNumber().length() != 10) {
            violationMessages.add("Number length should be 10");
        }

        if (!validateNumberRegex(phone.getNumber())) {
            violationMessages.add("Number should be alphanumeric");
        }

        if (!violationMessages.isEmpty()) {
            String formattedViolationMessages = violationMessages.stream()
                    .map(String::toString)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(formattedViolationMessages);
        }

    }

    public static boolean validateNumberRegex(String number) {
        if (number.length() != 10) {
        throw new RuntimeException("the phone number length have to be 10, but yours is : " + number.length() );
        }

        String regexPattern = "^[a-zA-Z0-9]+$"; // Regex pour alphanumérique (lettres minuscules, majuscules et chiffres)

        // Créez un objet Pattern en utilisant le regexPattern
        Pattern pattern = Pattern.compile(regexPattern);

        // Créez un objet Matcher en utilisant la chaîne d'entrée et le Pattern
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
