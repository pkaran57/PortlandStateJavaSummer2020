package edu.pdx.cs410J.pkaran.phonebill.domian.text;

import edu.pdx.cs410J.pkaran.phonebill.domian.PhoneBill;
import edu.pdx.cs410J.pkaran.phonebill.domian.PhoneCall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Generates PhoneBill based on its text representation
 */
public class TextParser {

    /**
     * Generates PhoneBill based on its text representation
     * @return a PhoneBill object based on its text representation
     */
    public static PhoneBill parse(String phoneCallTextDumperString) {
        List<String> lines = Arrays.asList(phoneCallTextDumperString.split("\n"));

        lines = lines.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if(lines.isEmpty() || allLinesBlank(lines)) {
            throw new IllegalStateException("All lines in response from servre are blank");
        }

        String customerName = null;
        List<PhoneCall> phoneCalls= new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).strip();

            if(i == 0) {
                if (line.isBlank()) {
                    throw new IllegalStateException("First line (containing customer name) cannot be blank");
                }
                customerName = line;
                continue;
            }

            if(line.isBlank()) {
                continue;
            }

            phoneCalls.add(PhoneCall.generateFromStringRepresentation(line));
        }

        return PhoneBill.PhoneBillBuilder.aPhoneBill()
                .withCustomerName(customerName)
                .withPhoneCalls(phoneCalls)
                .build();
    }

    /**
     * Checks if all lines are blank
     * @param lines lines to check
     * @return true if all lines are blank, false otherwise
     */
    static boolean allLinesBlank(List<String> lines) {
        return lines.stream().allMatch(String::isBlank);
    }
}
