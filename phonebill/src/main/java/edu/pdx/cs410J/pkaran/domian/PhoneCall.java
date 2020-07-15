package edu.pdx.cs410J.pkaran.domian;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * a class to represent a Phone call make by a caller to a callee and to track the duration of the call
 */
public class PhoneCall extends AbstractPhoneCall {

    private static final String STRING_FORMAT_DELIMITER = "\\|";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M/d/uuuu H:mm");

    private final String caller;
    private final String callee;
    private final String startTime;
    private final String endTime;

    /**
     * All args constructor which performs validation for all of the parameters. Exception can be thrown if any of the parameters are invalid.
     *
     * @param caller caller's number
     * @param callee callee's number
     * @param startTime timestamp for when the call started
     * @param endTime timestamp for when the call ended
     */
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

    /**
     * Check if the phone number is valid
     * @param phoneNumber phone number to check
     * @return true if valid, false otherwise
     */
    static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && Pattern.matches("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d", phoneNumber);
    }

    /**
     * Check if the timestamp is valid
     * @param timestamp timestamp to check
     * @return true if valid, false otherwise
     */
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

    /**
     * Builder for the PhoneCall class
     */
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

        /**
         * build a PhoneCall class instance
         * @return an instance of the PhoneCall class
         */
        public PhoneCall build() {
            return new PhoneCall(caller, callee, startTime, endTime);
        }
    }

    public String getStringRepresentation() {
        StringJoiner joiner = new StringJoiner(STRING_FORMAT_DELIMITER);

        joiner.add(this.getCaller())
              .add(this.getCallee())
              .add(this.getStartTimeString())
              .add(this.getEndTimeString());

        return joiner.toString();
    }

    public static PhoneCall generateFromStringRepresentation(String stringRepresentation) {
        List<String> phoneCallFields = Arrays.asList(stringRepresentation.split(STRING_FORMAT_DELIMITER));

        int numOfFields = phoneCallFields.size();
        if (numOfFields != 4) {
            throw new IllegalStateException("Expected string representation of PhoneCall contain 4 fields but got " + numOfFields);
        }

        return PhoneCallBuilder.aPhoneCall()
                .withCaller(phoneCallFields.get(0))
                .withCallee(phoneCallFields.get(1))
                .withStartTime(phoneCallFields.get(2))
                .withEndTime(phoneCallFields.get(3))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneCall phoneCall = (PhoneCall) o;
        return caller.equals(phoneCall.caller) &&
                callee.equals(phoneCall.callee) &&
                startTime.equals(phoneCall.startTime) &&
                endTime.equals(phoneCall.endTime);
    }
}
