package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.pkaran.PhoneBillRestClient.PhoneBillRestException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static edu.pdx.cs410J.pkaran.Project4.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    public void emptyCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertEquals("Did not find the '-host' option. Please specify it and try again. Check out the README for more info.", result.getTextWrittenToStandardError());
    }

    @Test
    public void noProgramArguments() {
        MainMethodResult result = invokeMain( Project4.class, PORT_OPTION, PORT, HOST_OPTION, HOSTNAME );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertEquals("Expected a total of 1 or 9 arguments of the form: [customer callerNumber calleeNumber start-date start-time am/pm end-date end-time am/pm] (where only customer should be specified if only 1 arg is present) but got the following 0 : []", out);
    }

    @Test
    public void readMe() {
        MainMethodResult result = invokeMain( Project4.class, READ_ME_OPTION);
        assertThat(result.getTextWrittenToStandardOut(), containsString("Student name: Karan Patel, CS410J Project 4: A REST-ful Phone Bill Web Service"));
    }

    @Test(expected = PhoneBillRestException.class)
    public void noBillFound() throws Throwable {
        try {
            invokeMain(Project4.class, HOST_OPTION, HOSTNAME, PORT_OPTION, PORT, "Dave");
        } catch (UncaughtExceptionInMain ex) {
            throw ex.getCause();
        }
    }

    @Test
    public void test4AddDefinition() {
        String word = "WORD";
        String definition = "DEFINITION";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, word, definition );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(Messages.definedWordAs(word, definition)));

        result = invokeMain( Project4.class, HOSTNAME, PORT, word );
        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(Messages.formatDictionaryEntry(word, definition)));

        result = invokeMain( Project4.class, HOSTNAME, PORT );
        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(Messages.formatDictionaryEntry(word, definition)));
    }
}