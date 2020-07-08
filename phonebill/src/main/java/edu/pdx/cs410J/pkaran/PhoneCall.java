package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {

    private final String caller;
    private final String callee;
    private final String startTime;
    private final String endTime;

    private PhoneCall(String caller, String callee, String startTime, String endTime) {
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
