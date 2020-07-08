package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Assert;
import org.junit.Test;

import static edu.pdx.cs410J.pkaran.Project1.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
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

        Assert.assertEquals("First Last's phone bill with 1 phone calls", result.getTextWrittenToStandardOut());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());

        result = invokeMain(PRINT_OPTION, "First", "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        Assert.assertEquals("First's phone bill with 1 phone calls", result.getTextWrittenToStandardOut());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());
    }

    @Test
    public void missingArgs() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        Assert.assertEquals("Expected a total of 7 arguments of the form: [customer callerNumber calleeNumber start-date start-time end-date end-time] but got the following 6 : [555-555-5556 666-666-6667 1/15/2020 19:39 02/1/2020 1:03]", result.getTextWrittenToStandardError());
    }
}