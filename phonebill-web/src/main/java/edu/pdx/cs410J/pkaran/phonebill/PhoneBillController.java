package edu.pdx.cs410J.pkaran.phonebill;

import edu.pdx.cs410J.pkaran.phonebill.domian.PhoneBill;
import edu.pdx.cs410J.pkaran.phonebill.domian.PhoneCall;
import edu.pdx.cs410J.pkaran.phonebill.domian.text.TextDumper;

import java.util.*;

public class PhoneBillController {

    private final List<PhoneBill> phoneBillList = new ArrayList<>();

    public String getPhoneCalls(String customerName, Date startDate, Date endDate) {
        Optional<PhoneBill> phoneBillOptional = getPhoneBill(customerName);
        return phoneBillOptional.isPresent() ? TextDumper.dump(phoneBillOptional.get(), startDate, endDate) : null;
    }

    public void addPhoneCall(String customerName, PhoneCall phoneCall) {
        Optional<PhoneBill> phoneBillOptional = getPhoneBill(customerName);

        if (phoneBillOptional.isPresent()) {
            PhoneBill phoneBill = phoneBillOptional.get();
            phoneBill.addPhoneCall(phoneCall);
        } else {
            PhoneBill phoneBill = PhoneBill.PhoneBillBuilder.aPhoneBill()
                                            .withCustomerName(customerName)
                                            .withPhoneCalls(Collections.singletonList(phoneCall))
                                            .build();

            phoneBillList.add(phoneBill);
        }
    }

    private Optional<PhoneBill> getPhoneBill(String customerName) {
        return phoneBillList.stream()
                .filter(phoneBill -> phoneBill.getCustomer().equals(customerName))
                .findAny();
    }
}