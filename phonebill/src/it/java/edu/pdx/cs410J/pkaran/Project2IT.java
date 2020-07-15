package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.pkaran.domian.text.TextParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static edu.pdx.cs410J.pkaran.Project2.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class Project2IT extends InvokeMainTestCase {

    final File resourcesDirectory = new File("src/it/resources/edu/pdx/cs410J/pkaran");

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

        assertEquals(READ_ME, result.getTextWrittenToStandardOut());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());
    }

    @Test
    public void readme_ignoreOtherOptions() {
        MainMethodResult result = invokeMain(READ_ME_OPTION, PRINT_OPTION, "First Last", "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertEquals(READ_ME, result.getTextWrittenToStandardOut());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());
    }

    @Test
    public void allArgsPassedCorrectly() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "First Last", "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertEquals("Phone call from 555-555-5556 to 666-666-6667 from 1/15/2020 19:39 to 02/1/2020 1:03", result.getTextWrittenToStandardOut());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());

        result = invokeMain(PRINT_OPTION, "First", "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertEquals("Phone call from 555-555-5556 to 666-666-6667 from 1/15/2020 19:39 to 02/1/2020 1:03", result.getTextWrittenToStandardOut());
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
        assertEquals("Expected a total of 7 arguments of the form: [customer callerNumber calleeNumber start-date start-time end-date end-time] but got the following 6 : [555-555-5556 666-666-6667 1/15/2020 19:39 02/1/2020 1:03]", result.getTextWrittenToStandardError());
    }

    @Test
    public void invalidCaller() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "name", "555555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertThat(result.getExitCode(), equalTo(1));
        assertEquals("Caller phone number is invalid. Phone numbers should have the form nnn-nnn-nnnn where n is a number 0-9 but got 555555-5556", result.getTextWrittenToStandardError());
    }

    @Test
    public void invalidCallee() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "name", "555-555-5556", "6666666667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertThat(result.getExitCode(), equalTo(1));
        assertEquals("Callee phone number is invalid. Phone numbers should have the form nnn-nnn-nnnn where n is a number 0-9 but got 6666666667", result.getTextWrittenToStandardError());
    }

    @Test
    public void invalidStartTime() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "name", "555-555-5556", "666-666-6667", "1-15-2020", "19:39", "02/1/2020", "1:03");

        assertThat(result.getExitCode(), equalTo(1));
        assertEquals("Start time is invalid. It should be in the following format: mm/dd/yyyy hh:mm but got 1-15-2020 19:39", result.getTextWrittenToStandardError());
    }

    @Test
    public void invalidEndTime() {
        MainMethodResult result = invokeMain(PRINT_OPTION, "name", "555-555-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1-03");

        assertThat(result.getExitCode(), equalTo(1));
        assertEquals("End time is invalid. It should be in the following format: mm/dd/yyyy hh:mm but got 02/1/2020 1-03", result.getTextWrittenToStandardError());
    }

    @Test
    public void billFilePresent() throws IOException, ParserException {
        Path file = Paths.get(resourcesDirectory.getAbsolutePath(), "Jane Taylor-PhoneBill.txt");
        Files.writeString(file, "Jane Taylor\n" +
                "555-555-5556|666-666-6667|1/15/2020 19:39|02/1/2020 1:03\n" +
                "777-555-5556|666-777-6667|1/15/2020 19:49|02/1/2020 1:13\n" +
                "555-555-8888|666-666-8888|1/15/2020 19:59|02/1/2020 1:23");

        MainMethodResult result = invokeMain(TEXT_FILE_OPTION, file.toAbsolutePath().toString(), PRINT_OPTION, "Jane Taylor", "555-999-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertEquals("Phone call from 555-999-5556 to 666-666-6667 from 1/15/2020 19:39 to 02/1/2020 1:03", result.getTextWrittenToStandardOut());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());

        TextParser textParser = new TextParser(file);
        AbstractPhoneBill phoneBill = textParser.parse();

        assertEquals("Jane Taylor", phoneBill.getCustomer());
        assertEquals(4, phoneBill.getPhoneCalls().size());
    }

    @Test
    public void customerNameDifferent() throws IOException, ParserException {
        Path file = Paths.get(resourcesDirectory.getAbsolutePath(), "Jane Taylor-PhoneBill - customerNameDifferent.txt");
        Files.writeString(file, "Jane Taylor\n" +
                "555-555-5556|666-666-6667|1/15/2020 19:39|02/1/2020 1:03\n" +
                "777-555-5556|666-777-6667|1/15/2020 19:49|02/1/2020 1:13\n" +
                "555-555-8888|666-666-8888|1/15/2020 19:59|02/1/2020 1:23");

        MainMethodResult result = invokeMain(TEXT_FILE_OPTION, file.toAbsolutePath().toString(), PRINT_OPTION, "Tom", "555-999-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertEquals("Customer name in file (Jane Taylor) does not match the customer name in the argument (Tom)", result.getTextWrittenToStandardError());

        // cleanup
        new File(String.valueOf(file)).deleteOnExit();
    }

    @Test
    public void billFileAbsent() throws ParserException {
        Path file = Paths.get(resourcesDirectory.getAbsolutePath(), "Kate-PhoneBill.txt");

        MainMethodResult result = invokeMain(TEXT_FILE_OPTION, file.toAbsolutePath().toString(), PRINT_OPTION, "Kate", "555-999-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertEquals("Phone call from 555-999-5556 to 666-666-6667 from 1/15/2020 19:39 to 02/1/2020 1:03", result.getTextWrittenToStandardOut());
        Assert.assertTrue(result.getTextWrittenToStandardError().isEmpty());

        TextParser textParser = new TextParser(file);
        AbstractPhoneBill phoneBill = textParser.parse();

        assertEquals("Kate", phoneBill.getCustomer());
        assertEquals(1, phoneBill.getPhoneCalls().size());

        // cleanup
        new File(String.valueOf(file)).deleteOnExit();
    }

    @Test
    public void  badBillFile() {
        Path file = Paths.get(resourcesDirectory.getAbsolutePath(), "BadBillFile.txt");

        MainMethodResult result = invokeMain(TEXT_FILE_OPTION, file.toAbsolutePath().toString(), PRINT_OPTION, "Tom", "555-999-5556", "666-666-6667", "1/15/2020", "19:39", "02/1/2020", "1:03");

        assertEquals("Expected string representation of PhoneCall to contain 4 fields but got 1 field(s).\n" +
                "Following is the expected representation of a Phone call that was expected: caller|callee|start-time|end-time", result.getTextWrittenToStandardError());
    }
}