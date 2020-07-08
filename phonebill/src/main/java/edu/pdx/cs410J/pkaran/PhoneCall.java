package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.regex.Pattern;

public class PhoneCall extends AbstractPhoneCall {

    private final String caller;
    private final String callee;
    private final String startTime;
    private final String endTime;

    private PhoneCall(String caller, String callee, String startTime, String endTime) {

        if (isValidPhoneNumber(caller)) {
            this.caller = caller;
        } else {
            throw new IllegalArgumentException(String.format("Caller phone number is invalid. Expected a number of format nnn-nnn-nnnn where n is a digit but got %s", caller));
        }

        if (isValidPhoneNumber(caller)) {
            this.callee = callee;
        } else {
            throw new IllegalArgumentException(String.format("Callee phone number is invalid. Expected a number of format nnn-nnn-nnnn where n is a digit but got %s", callee));
        }

        this.startTime = startTime;
        this.endTime = endTime;
    }

    static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && Pattern.matches("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d", phoneNumber);
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

    public static final class PhoneCallBuilder {
        private String caller;
        private String callee;
        private String startTime;
        private String endTime;

        private PhoneCallBuilder() {
        }

        public static PhoneCallBuilder aPhoneCall() {
            return new PhoneCallBuilder();
        }

        public PhoneCallBuilder withCaller(String caller) {
            this.caller = caller;
            return this;
        }

        public PhoneCallBuilder withCallee(String callee) {
            this.callee = callee;
            return this;
        }

        public PhoneCallBuilder withStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        public PhoneCallBuilder withEndTime(String endTime) {
            this.endTime = endTime;
            return this;
        }

        public PhoneCall build() {
            return new PhoneCall(caller, callee, startTime, endTime);
        }
    }
}
