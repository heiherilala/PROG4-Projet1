package com.hei.project2p1.repository.firm.entity.validator;

import com.hei.project2p1.exception.BadRequestException;
import com.hei.project2p1.repository.firm.entity.CompanyEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CompanyEntityValidator implements Consumer<CompanyEntity> {
  @Override public void accept(CompanyEntity company) {
    Set<String> violationMessages = new HashSet<>();
    if (company.getNif() != null) {
      if (!validateCinRegex(company.getNif())) {
        violationMessages.add("Nif should have alphanumeric format");
      }
    }
    if (company.getStat() != null) {
      if (!validateCinRegex(company.getStat())) {
        violationMessages.add("Stat should have alphanumeric format");
      }
    }
    if (!violationMessages.isEmpty()) {
      String formattedViolationMessages = violationMessages.stream()
          .map(String::toString)
          .collect(Collectors.joining(". "));
      throw new BadRequestException(formattedViolationMessages);
    }
  }
  public static boolean validateCinRegex(String number) {
    String regexPattern = "^[0-9\\s]+$";
    Pattern pattern = Pattern.compile(regexPattern);
    Matcher matcher = pattern.matcher(number);
    return matcher.matches();
  }
}