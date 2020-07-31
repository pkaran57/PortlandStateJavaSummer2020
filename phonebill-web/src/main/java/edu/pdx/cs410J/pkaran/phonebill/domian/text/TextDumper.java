package edu.pdx.cs410J.pkaran.phonebill.domian.text;

import edu.pdx.cs410J.pkaran.phonebill.domian.PhoneBill;
import edu.pdx.cs410J.pkaran.phonebill.domian.PhoneCall;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates and returns text representation of a PhoneBill
 */
public class TextDumper {

    /**
     * Generates and returns text representation of PhoneBill
     * @param phoneBill phone bill whose text representation needs to be dumped
     */
    public static String dump(PhoneBill<PhoneCall> phoneBill, Date startTime, Date endTime) {
        List<String> lines = new LinkedList<>();
        lines.add(phoneBill.getCustomer());

        Collection<PhoneCall> phoneCalls = (startTime == null && endTime == null) ? phoneBill.getPhoneCalls() : phoneBill.getPhoneCallsBetween(startTime, endTime);

        if (phoneCalls != null && !phoneCalls.isEmpty()) {
            List<String> phoneCallLines = phoneCalls.stream()
                                                    .map(PhoneCall::getStringRepresentation)
                                                    .collect(Collectors.toList());

            lines.addAll(phoneCallLines);
        }

        return String.join(System.lineSeparator(), lines);
    }
}
