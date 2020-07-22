package edu.pdx.cs410J.pkaran.domian;

import org.junit.Assert;
import org.junit.Test;

import static edu.pdx.cs410J.pkaran.domian.PhoneCall.isTimeStampValid;
import static edu.pdx.cs410J.pkaran.domian.PhoneCall.isValidPhoneNumber;
import static org.junit.Assert.assertEquals;


/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {

  private static final String CALLER = "555-555-5556";
  private static final String CALLEE = "666-666-6667";
  private static final String START_TIME = "1/15/2020 9:39 am";
  private static final String END_TIME = "02/1/2020 1:03 pm";

  private static final PhoneCall VALID_PHONE_CALL = PhoneCall.PhoneCallBuilder.aPhoneCall()
          .withCaller(CALLER)
          .withCallee(CALLEE)
          .withStartTime(START_TIME)
          .withEndTime(END_TIME)
          .build();

  @Test
  public void getCaller() {
    assertEquals(CALLER, VALID_PHONE_CALL.getCaller());
  }

  @Test
  public void getCallee() {
    assertEquals(CALLEE, VALID_PHONE_CALL.getCallee());
  }

  @Test
  public void getStartTimeString() {
    assertEquals("1/15/20, 9:39 AM", VALID_PHONE_CALL.getStartTimeString());
  }

  @Test
  public void getEndTimeString() {
    assertEquals("2/1/20, 1:03 PM", VALID_PHONE_CALL.getEndTimeString());
  }

  @Test
  public void test_isValidPhoneNumber() {
    Assert.assertTrue(isValidPhoneNumber("555-454-2382"));

    Assert.assertFalse(isValidPhoneNumber(null));
    Assert.assertFalse(isValidPhoneNumber("555454-2382"));
    Assert.assertFalse(isValidPhoneNumber("555-454-238"));
    Assert.assertFalse(isValidPhoneNumber("0-0-0"));
    Assert.assertFalse(isValidPhoneNumber("5554542382"));
  }

  @Test
  public void test_isTimeStampValid() {
    Assert.assertTrue(isTimeStampValid("01/15/2020 9:39 am"));
    Assert.assertTrue(isTimeStampValid("1/2/2020 1:03 pm"));

    Assert.assertFalse(isTimeStampValid(null));
    Assert.assertFalse(isTimeStampValid("01-15-2020 9:39 am"));
    Assert.assertFalse(isTimeStampValid("1/2/2020"));
    Assert.assertFalse(isTimeStampValid("1:03 pm"));
  }

    @Test
    public void getStringRepresentation() {
      PhoneCall phoneCall = PhoneCall.PhoneCallBuilder.aPhoneCall()
              .withCaller("555-555-5556")
              .withCallee("666-666-6667")
              .withStartTime("1/15/2020 9:39 am")
              .withEndTime("02/1/2020 1:03 pm")
              .build();

      String generatedStringRepresentation = phoneCall.getStringRepresentation();

      assertEquals("555-555-5556|666-666-6667|1/15/2020 9:39 AM|2/1/2020 1:3 PM", generatedStringRepresentation);
    }

    @Test
    public void generateFromStringRepresentation() {
      String phoneCallString = "555-555-5556|666-666-6667|1/15/2020 9:39 am|02/1/2020 1:03 PM";

      PhoneCall phoneCall = PhoneCall.generateFromStringRepresentation(phoneCallString);

      assertEquals("555-555-5556", phoneCall.getCaller());
      assertEquals("666-666-6667", phoneCall.getCallee());
      assertEquals("1/15/20, 9:39 AM", phoneCall.getStartTimeString());
      assertEquals("2/1/20, 1:03 PM", phoneCall.getEndTimeString());
    }

  @Test
  public void generateFromInvalidStringRepresentation() {
    String phoneCallString = "invlaid";

    Assert.assertThrows("Expected string representation of PhoneCall to contain 4 fields but got 1 field(s).\nFollowing is the expected representation of a Phone call that was expected: caller|callee|start-time|end-time",
            IllegalStateException.class,
            () -> PhoneCall.generateFromStringRepresentation(phoneCallString));
  }
}
