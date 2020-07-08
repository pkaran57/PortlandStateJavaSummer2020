package edu.pdx.cs410J.pkaran;

import org.junit.Assert;
import org.junit.Test;

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
}
