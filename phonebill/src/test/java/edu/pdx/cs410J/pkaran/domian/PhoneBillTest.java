package edu.pdx.cs410J.pkaran.domian;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static edu.pdx.cs410J.pkaran.domian.PhoneBill.PhoneBillBuilder;

public class PhoneBillTest {

    private static final PhoneCall PHONE_CALL = PhoneCall.PhoneCallBuilder.aPhoneCall()
                                                                            .withCaller("555-555-5556")
                                                                            .withCallee("666-666-6667")
                                                                            .withStartTime("1/15/2020 9:39 am")
                                                                            .withEndTime("02/1/2020 1:03 pm")
                                                                            .build();

    @Test
    public void sortPhoneCalls_differentStartTimes() {
        String customerName = "customer-name";

        PhoneCall aPhoneCall = PhoneCall.PhoneCallBuilder.aPhoneCall()
                .withCaller("555-555-5556")
                .withCallee("666-666-6667")
                .withStartTime("1/15/2020 8:39 am")
                .withEndTime("02/1/2020 1:03 pm")
                .build();

        List phoneCallsList = new ArrayList();
        phoneCallsList.add(PHONE_CALL);
        phoneCallsList.add(aPhoneCall);

        PhoneBill phoneBill = PhoneBillBuilder.aPhoneBill().withCustomerName(customerName).withPhoneCalls(phoneCallsList).build();

        Assert.assertEquals(customerName, phoneBill.getCustomer());
        Collection phoneCalls = phoneBill.getPhoneCalls();
        List<Object> phoneCallList = Arrays.asList(phoneCalls.toArray());

        Assert.assertEquals(2, phoneCallList.size());
        Assert.assertEquals(aPhoneCall, phoneCallList.get(0));
        Assert.assertEquals(PHONE_CALL, phoneCallList.get(1));
    }

    @Test
    public void sortPhoneCalls_sameStartTime() {
        String customerName = "customer-name";

        PhoneCall aPhoneCall = PhoneCall.PhoneCallBuilder.aPhoneCall()
                .withCaller("555-555-5552")
                .withCallee("666-666-6667")
                .withStartTime("1/15/2020 9:39 am")
                .withEndTime("02/1/2020 1:03 pm")
                .build();

        List phoneCallsList = new ArrayList();
        phoneCallsList.add(PHONE_CALL);
        phoneCallsList.add(aPhoneCall);

        PhoneBill phoneBill = PhoneBillBuilder.aPhoneBill().withCustomerName(customerName).withPhoneCalls(phoneCallsList).build();

        Assert.assertEquals(customerName, phoneBill.getCustomer());
        Collection phoneCalls = phoneBill.getPhoneCalls();
        List<Object> phoneCallList = Arrays.asList(phoneCalls.toArray());

        Assert.assertEquals(2, phoneCallList.size());
        Assert.assertEquals(aPhoneCall, phoneCallList.get(0));
        Assert.assertEquals(PHONE_CALL, phoneCallList.get(1));
    }


    @Test
    public void getCustomer() {
        String customerName = "customer-name";
        PhoneBill phoneBill = PhoneBillBuilder.aPhoneBill().withCustomerName(customerName).build();

        Assert.assertEquals(customerName, phoneBill.getCustomer());
    }

    @Test
    public void addPhoneCall() {
        PhoneBill<PhoneCall> phoneBill = PhoneBillBuilder.aPhoneBill().withCustomerName("customer").build();

        phoneBill.addPhoneCall(PHONE_CALL);

        Assert.assertNotNull(phoneBill.getPhoneCalls());
        Assert.assertEquals(1, phoneBill.getPhoneCalls().size());
        Assert.assertTrue(phoneBill.getPhoneCalls().contains(PHONE_CALL));
    }

    @Test
    public void getPhoneCalls_callsAdded() {
        List<PhoneCall> phoneCalls = List.of(PHONE_CALL, PHONE_CALL);

        PhoneBill<PhoneCall> phoneBill = PhoneBillBuilder.aPhoneBill().withCustomerName("customer").withPhoneCalls(phoneCalls).build();

        Assert.assertEquals(phoneCalls, phoneBill.getPhoneCalls());
    }

    @Test
    public void getPhoneCalls_noCallsAdded() {
        PhoneBill<PhoneCall> phoneBill = PhoneBillBuilder.aPhoneBill().withCustomerName("customer").build();

        Assert.assertNotNull(phoneBill.getPhoneCalls());
        Assert.assertTrue(phoneBill.getPhoneCalls().isEmpty());
    }
}