package edu.pdx.cs410J.pkaran.domian;

import org.junit.Assert;
import org.junit.Test;

import static edu.pdx.cs410J.pkaran.domian.PhoneCall.isTimeStampValid;
import static edu.pdx.cs410J.pkaran.domian.PhoneCall.isValidPhoneNumber;


/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {

  private static final String CALLER = "555-555-5556";
  private static final String CALLEE = "666-666-6667";
  private static final String START_TIME = "1/15/2020 19:39";
  private static final String END_TIME = "02/1/2020 1:03";

  private static final PhoneCall VALID_PHONE_CALL = PhoneCall.PhoneCallBuilder.aPhoneCall()
          .withCaller(CALLER)
          .withCallee(CALLEE)
          .withStartTime(START_TIME)
          .withEndTime(END_TIME)
          .build();

  @Test
  public void getCaller() {
    Assert.assertEquals(CALLER, VALID_PHONE_CALL.getCaller());
  }

  @Test
  public void getCallee() {
    Assert.assertEquals(CALLEE, VALID_PHONE_CALL.getCallee());
  }

  @Test
  public void getStartTimeString() {
    Assert.assertEquals(START_TIME, VALID_PHONE_CALL.getStartTimeString());
  }

  @Test
  public void getEndTimeString() {
    Assert.assertEquals(END_TIME, VALID_PHONE_CALL.getEndTimeString());
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
    Assert.assertTrue(isTimeStampValid("01/15/2020 19:39"));
    Assert.assertTrue(isTimeStampValid("1/2/2020 1:03"));

    Assert.assertFalse(isTimeStampValid(null));
    Assert.assertFalse(isTimeStampValid("01-15-2020 19:39"));
    Assert.assertFalse(isTimeStampValid("1/2/2020"));
    Assert.assertFalse(isTimeStampValid("1:03"));
  }
}
