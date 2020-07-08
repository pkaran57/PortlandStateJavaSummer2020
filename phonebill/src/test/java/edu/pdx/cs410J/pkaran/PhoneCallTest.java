package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.pkaran.PhoneCall.PhoneCallBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {

  @Test
  public void getCaller() {
    String caller = "caller";

    PhoneCall phoneCall = PhoneCallBuilder.aPhoneCall().withCaller(caller).build();
    Assert.assertEquals(caller, phoneCall.getCaller());
  }

  @Test
  public void getCallee() {
    String callee = "callee";

    PhoneCall phoneCall = PhoneCallBuilder.aPhoneCall().withCallee(callee).build();
    Assert.assertEquals(callee, phoneCall.getCallee());

  }

  @Test
  public void getStartTimeString() {
    String startTime = "startTime";

    PhoneCall phoneCall = PhoneCallBuilder.aPhoneCall().withStartTime(startTime).build();
    Assert.assertEquals(startTime, phoneCall.getStartTimeString());
  }

  @Test
  public void getEndTimeString() {
    String endTime = "endTime";

    PhoneCall phoneCall = PhoneCallBuilder.aPhoneCall().withEndTime(endTime).build();
    Assert.assertEquals(endTime, phoneCall.getEndTimeString());
  }
}
