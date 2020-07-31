package edu.pdx.cs410J.pkaran.phonebill.domian;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class representing a phone bill for a given customer and optionally containing details about phone calls
 */
public class PhoneBill<T extends PhoneCall> extends AbstractPhoneBill<T> {

    private final String customerName;
    private final List<T> phoneCalls;

    /**
     * all args constructor
     * @param customerName name of the customer
     * @param phoneCalls list of phone calls for the phone bill
     */
    private PhoneBill(String customerName, List<T> phoneCalls) {
        if(customerName == null) {
            throw new IllegalStateException("Customer name on Phone Bill is null");
        }

        if(customerName.isBlank()) {
            throw new IllegalStateException("Customer name on Phone Bill is blank");
        }

        this.customerName = customerName;
        this.phoneCalls = Objects.requireNonNullElseGet(phoneCalls, ArrayList::new);
        Collections.sort(this.phoneCalls);
    }

    @Override
    public String getCustomer() {
        return customerName;
    }

    @Override
    public void addPhoneCall(T abstractPhoneCall) {
        phoneCalls.add(abstractPhoneCall);
        Collections.sort(this.phoneCalls);
    }

    @Override
    public Collection<T> getPhoneCalls() {
        Collections.sort(this.phoneCalls);
        return phoneCalls;
    }

    public Collection<T> getPhoneCallsBetween(Date startDateTime, Date endDateTime) {
        Collections.sort(this.phoneCalls);

        List<T> callsInBetweenList = phoneCalls.stream()
                .filter(phoneCall -> phoneCall.getStartTime().after(startDateTime) && phoneCall.getStartTime().before(endDateTime))
                .collect(Collectors.toList());

        Collections.sort(callsInBetweenList);

        return callsInBetweenList;
    }

    public List<T> getPhoneCallsAsList() {
        Collections.sort(this.phoneCalls);
        return phoneCalls;
    }

    /**
     * Builder for the PhoneBill class
     */
    public static final class PhoneBillBuilder<T extends PhoneCall> {
        private String customerName;
        private List<T> phoneCalls;

        private PhoneBillBuilder() {
        }

        public static PhoneBillBuilder aPhoneBill() {
            return new PhoneBillBuilder();
        }

        public PhoneBillBuilder<T> withCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public PhoneBillBuilder<T> withPhoneCalls(List<T> phoneCalls) {
            this.phoneCalls = phoneCalls == null ? phoneCalls : new ArrayList<>(phoneCalls);
            return this;
        }

        /**
         * build a PhoneBill class instance
         * @return an instance of the PhoneBill class
         */
        public PhoneBill<T> build() {
            return new PhoneBill<T>(customerName, phoneCalls);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneBill<?> phoneBill = (PhoneBill<?>) o;
        return customerName.equals(phoneBill.customerName) &&
                phoneCalls.equals(phoneBill.phoneCalls);
    }
}
