package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {

    private final String caller;
    private final String callee;
    private final String startTime;
    private final String endTime;

    public PhoneCall() {
        this(null, null, null, null);
    }

    public PhoneCall(String caller, String callee, String startTime, String endTime) {
        this.caller = caller;
        this.callee = callee;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String getCaller() {
        return caller;
    }

    @Override
    public String getCallee() {
        return callee;
    }

    @Override
    public String getStartTimeString() {
        return startTime;
    }

    @Override
    public String getEndTimeString() {
        return endTime;
    }
}
