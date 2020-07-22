package edu.pdx.cs410J.pkaran.domian;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

/**
 * a class to represent a Phone call make by a caller to a callee and to track the duration of the call
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable {

    private static final String STRING_FORMAT_DELIMITER = "|";

    //example:  01/02/2020 9:16 pm
    private static final  SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("M/d/uuuu h:m a");
    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);

    private final String caller;
    private final String callee;
    private final Date startTime;
    private final Date endTime;

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
            this.startTime = parseTimeStamp(startTime);
        } else {
            throw new IllegalArgumentException(String.format("Start time is invalid. It should be in the following format: 'mm/dd/yyyy hh:mm am/pm' but got %s", startTime));
        }

        if(isTimeStampValid(endTime)) {
            this.endTime = parseTimeStamp(endTime);
        } else {
            throw new IllegalArgumentException(String.format("End time is invalid. It should be in the following format: 'mm/dd/yyyy hh:mm am/pm' but got %s", endTime));
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
        return parseTimeStamp(timestamp) != null;
    }

    /**
     * Parse timestamp into Date
     * @param timestamp timestamp to parse
     * @return parsed Date, if error encountered during parsing, null is returned
     */
    private static Date parseTimeStamp(String timestamp) {
        if (timestamp == null) {
            return null;
        }

        try {
            return SIMPLE_DATE_FORMAT.parse(timestamp);
        } catch (ParseException dateTimeParseException) {
            return null;
        }
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
        return DATE_FORMAT.format(startTime);
    }

    @Override
    public String getEndTimeString() {
        return DATE_FORMAT.format(endTime);
    }

    @Override
    public Date getStartTime() {
        return super.getStartTime();
    }

    @Override
    public Date getEndTime() {
        return super.getEndTime();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param object the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Object object) {
        return Comparator
                .comparing(PhoneCall::getStartTime)
                .thenComparing(PhoneCall::getCaller)
                .compare(this, (PhoneCall) object);
    }

    public Duration getDuration() {
        return Duration.between(startTime.toInstant(), endTime.toInstant());
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

    /**
     * Generates a string representation of the PhoneCall object
     * @return a string representation of the PhoneCall object
     */
    public String getStringRepresentation() {
        StringJoiner joiner = new StringJoiner(STRING_FORMAT_DELIMITER);

        joiner.add(this.getCaller())
              .add(this.getCallee())
              .add(this.getStartTimeString())
              .add(this.getEndTimeString());

        return joiner.toString();
    }

    /**
     * Creates a PhoneCall object from the stringRepresentation
     * @param stringRepresentation string representation of the PhoneCall object
     * @return a PhoneCall object generated from the stringRepresentation
     */
    public static PhoneCall generateFromStringRepresentation(String stringRepresentation) {
        List<String> phoneCallFields = Arrays.asList(stringRepresentation.split(Pattern.quote(STRING_FORMAT_DELIMITER)));

        int numOfFields = phoneCallFields.size();
        if (numOfFields != 4) {
            String errorMessage = String.format("Expected string representation of PhoneCall to contain 4 fields but got %d field(s).\n" +
                    "Following is the expected representation of a Phone call that was expected: caller|callee|start-time|end-time", numOfFields);
            throw new IllegalStateException(errorMessage);
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
