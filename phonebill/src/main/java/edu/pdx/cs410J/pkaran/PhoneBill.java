package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PhoneBill<T extends AbstractPhoneCall> extends AbstractPhoneBill<T> {

    private final String customerName;
    private final List<T> phoneCalls;

    public PhoneBill(String customerName, List<T> phoneCalls) {
        this.customerName = customerName;
        this.phoneCalls = Objects.requireNonNullElseGet(phoneCalls, ArrayList::new);
    }

    @Override
    public String getCustomer() {
        return customerName;
    }

    @Override
    public void addPhoneCall(T abstractPhoneCall) {
        phoneCalls.add(abstractPhoneCall);
    }

    @Override
    public Collection<T> getPhoneCalls() {
        return phoneCalls;
    }
}
