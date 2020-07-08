package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class PhoneCall extends AbstractPhoneCall {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M/d/uuuu H:mm");

    private final String caller;
    private final String callee;
    private final String startTime;
    private final String endTime;

    private PhoneCall(String caller, String callee, String startTime, String endTime) {

        if (isValidPhoneNumber(caller)) {
            this.caller = caller;
        } else {
            throw new IllegalArgumentException(String.format("Caller phone number is invalid. Phone numbers should have the form nnn-nnn-nnnn where n is a number 0-9 but got %s", caller));
        }

        if (isValidPhoneNumber(callee)) {
            this.callee = callee;
        } else {
            throw new IllegalArgumentException(String.format("Callee phone number is invalid. Phone numbers should have the form nnn-nnn-nnnn where n is a number 0-9 but got %s", callee));
        }

        if(isTimeStampValid(startTime)) {
            this.startTime = startTime;
        } else {
            throw new IllegalArgumentException(String.format("Start time is invalid. It should be in the following format: mm/dd/yyyy hh:mm but got %s", startTime));
        }

        if(isTimeStampValid(endTime)) {
            this.endTime = endTime;
        } else {
            throw new IllegalArgumentException(String.format("End time is invalid. It should be in the following format: mm/dd/yyyy hh:mm but got %s", endTime));
        }
    }

    static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && Pattern.matches("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d", phoneNumber);
    }

    static boolean isTimeStampValid(String timestamp) {
        if (timestamp == null) {
            return false;
        }

        try {
            LocalDate.parse(timestamp, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException dateTimeParseException) {
            return false;
        }

       return true;
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
