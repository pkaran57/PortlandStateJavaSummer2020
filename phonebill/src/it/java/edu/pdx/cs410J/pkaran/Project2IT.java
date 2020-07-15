package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Assert;
import org.junit.Test;

import static edu.pdx.cs410J.pkaran.Project2.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();

    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

    /**
     * Tests that prints read me
     */
    @Test
    public void readme() {
        MainMethodResult result = invokeMain(READ_ME_OPTION);

        Assert.assertEquals(READ_ME, result.getTextWrittenToStandardOut());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());
    }

    @Test
    public void allArgsPassedCorrectly() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "First Last", "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        Assert.assertEquals("Phone call from 555-555-5556 to 666-666-6667 from 1/15/2020 19:39 to 02/1/2020 1:03", result.getTextWrittenToStandardOut());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());

        result = invokeMain(PRINT_OPTION, "First", "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        Assert.assertEquals("Phone call from 555-555-5556 to 666-666-6667 from 1/15/2020 19:39 to 02/1/2020 1:03", result.getTextWrittenToStandardOut());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());
    }

    @Test
    public void noPrintOption() {
        MainMethodResult result = invokeMain("First Last", "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        Assert.assertTrue(result.getTextWrittenToStandardOut().isEmpty());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());
    }

    @Test
    public void missingArgs() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertThat(result.getExitCode(), equalTo(1));
        Assert.assertEquals("Expected a total of 7 arguments of the form: [customer callerNumber calleeNumber start-date start-time end-date end-time] but got the following 6 : [555-555-5556 666-666-6667 1/15/2020 19:39 02/1/2020 1:03]", result.getTextWrittenToStandardError());
    }

    @Test
    public void invalidCaller() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "name", "555555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertThat(result.getExitCode(), equalTo(1));
        Assert.assertEquals("Caller phone number is invalid. Phone numbers should have the form nnn-nnn-nnnn where n is a number 0-9 but got 555555-5556", result.getTextWrittenToStandardError());
    }

    @Test
    public void invalidCallee() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "name", "555-555-5556", "6666666667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertThat(result.getExitCode(), equalTo(1));
        Assert.assertEquals("Callee phone number is invalid. Phone numbers should have the form nnn-nnn-nnnn where n is a number 0-9 but got 6666666667", result.getTextWrittenToStandardError());
    }

    @Test
    public void invalidStartTime() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "name", "555-555-5556", "666-666-6667", "1-15-2020", "19:39", "02/1/2020", "1:03");

        assertThat(result.getExitCode(), equalTo(1));
        Assert.assertEquals("Start time is invalid. It should be in the following format: mm/dd/yyyy hh:mm but got 1-15-2020 19:39", result.getTextWrittenToStandardError());
    }

    @Test
    public void invalidEndTime() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "name", "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1-03");

        assertThat(result.getExitCode(), equalTo(1));
        Assert.assertEquals("End time is invalid. It should be in the following format: mm/dd/yyyy hh:mm but got 02/1/2020 1-03", result.getTextWrittenToStandardError());
    }
}