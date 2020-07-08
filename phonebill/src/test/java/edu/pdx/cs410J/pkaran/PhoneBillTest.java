package edu.pdx.cs410J.pkaran;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static edu.pdx.cs410J.pkaran.PhoneBill.PhoneBillBuilder;

public class PhoneBillTest {

    @Test
    public void getCustomer() {
        String customerName = "customer-name";
        PhoneBill phoneBill = PhoneBillBuilder.aPhoneBill().withCustomerName(customerName).build();

        Assert.assertEquals(customerName, phoneBill.getCustomer());
    }

    @Test
    public void addPhoneCall() {
        PhoneBill<PhoneCall> phoneBill = PhoneBillBuilder.aPhoneBill().build();
        PhoneCall phoneCall = PhoneCall.PhoneCallBuilder.aPhoneCall().build();

        phoneBill.addPhoneCall(phoneCall);

        Assert.assertNotNull(phoneBill.getPhoneCalls());
        Assert.assertEquals(1, phoneBill.getPhoneCalls().size());
        Assert.assertTrue(phoneBill.getPhoneCalls().contains(phoneCall));
    }

    @Test
    public void getPhoneCalls_callsAdded() {
        PhoneCall call1 = PhoneCall.PhoneCallBuilder.aPhoneCall().build();
        PhoneCall call2 = PhoneCall.PhoneCallBuilder.aPhoneCall().build();
        List<PhoneCall> phoneCalls = List.of(call1, call2);

        PhoneBill<PhoneCall> phoneBill = PhoneBillBuilder.aPhoneBill().withPhoneCalls(phoneCalls).build();

        Assert.assertEquals(phoneCalls, phoneBill.getPhoneCalls());
    }

    @Test
    public void getPhoneCalls_noCallsAdded() {
        PhoneBill<PhoneCall> phoneBill = PhoneBillBuilder.aPhoneBill().build();

        Assert.assertNotNull(phoneBill.getPhoneCalls());
        Assert.assertTrue(phoneBill.getPhoneCalls().isEmpty());
    }
}