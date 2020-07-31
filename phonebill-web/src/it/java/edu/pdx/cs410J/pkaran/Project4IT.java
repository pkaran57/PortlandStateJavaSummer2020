package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.InvokeMainTestCase;
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
    public void test1_emptyCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertEquals("Did not find the '-host' option. Please specify it and try again. Check out the README for more info.", result.getTextWrittenToStandardError());
    }

    @Test
    public void test2_noProgramArguments() {
        MainMethodResult result = invokeMain( Project4.class, PORT_OPTION, PORT, HOST_OPTION, HOSTNAME );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertEquals("Expected a total of 1 or 9 arguments of the form: [customer callerNumber calleeNumber start-date start-time am/pm end-date end-time am/pm] (where only customer should be specified if only 1 arg is present) but got the following 0 : []", out);
    }

    @Test
    public void test3_readMe() {
        MainMethodResult result = invokeMain( Project4.class, READ_ME_OPTION);
        assertThat(result.getTextWrittenToStandardOut(), containsString("Student name: Karan Patel, CS410J Project 4: A REST-ful Phone Bill Web Service"));
    }

    @Test
    public void test4_noBillFound() {
        MainMethodResult result = invokeMain(Project4.class, HOST_OPTION, HOSTNAME, PORT_OPTION, PORT, "Dave");

        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();

        assertEquals("Got an HTTP Status Code of 404 with the following response from server: Did not find any phone calls for customer Dave", out);
    }


    @Test
    public void test5_addNewPhoneBill() {
        MainMethodResult result = invokeMain(Project4.class, HOST_OPTION, HOSTNAME, PORT_OPTION, PORT, "Dave", "503-245-2345", "765-389-1273", "02/27/2020" ,"8:56", "am", "02/27/2020", "10:27", "am");

        String out = result.getTextWrittenToStandardOut();

        assertEquals("Phone Call added to the phone bill for Dave", out.strip());
    }

    @Test
    public void test6_getUserWith1Call() {
        // search for user's calls
        MainMethodResult result = invokeMain(Project4.class, HOST_OPTION, HOSTNAME, PORT_OPTION, PORT, "Dave");
        String out = result.getTextWrittenToStandardOut();
        assertEquals("----------------------------------------------------------" + System.lineSeparator() +
                "Phone Bill for customer 'Dave':" + System.lineSeparator() +
                 System.lineSeparator() +
                "Following are the phone calls in the phone bill:" + System.lineSeparator() +
                 System.lineSeparator() +
                "Caller         Callee         Start Time             End Time               Duration (in seconds)" + System.lineSeparator() +
                "503-245-2345   765-389-1273   2/27/20, 8:56 AM       2/27/20, 10:27 AM      5460           " + System.lineSeparator() +
                 System.lineSeparator() +
                "----------------------------------------------------------", out.strip());
    }

    @Test
    public void test7_addPhoneCallToExistingPhoneBill() {
        MainMethodResult result = invokeMain(Project4.class, HOST_OPTION, HOSTNAME, PORT_OPTION, PORT, "Dave", "503-245-3333", "765-389-4444", "02/27/2019" ,"8:56", "am", "02/27/2019", "10:27", "am");

        String out = result.getTextWrittenToStandardOut();

        assertEquals("Phone Call added to the phone bill for Dave", out.strip());
    }

    @Test
    public void test8_getUserWithMultipleCalls() {
        // search for user's calls
        MainMethodResult result = invokeMain(Project4.class, HOST_OPTION, HOSTNAME, PORT_OPTION, PORT, "Dave");
        String out = result.getTextWrittenToStandardOut();
        assertEquals("----------------------------------------------------------" + System.lineSeparator() +
                "Phone Bill for customer 'Dave':" + System.lineSeparator() +
                System.lineSeparator() +
                "Following are the phone calls in the phone bill:" + System.lineSeparator() +
                System.lineSeparator() +
                "Caller         Callee         Start Time             End Time               Duration (in seconds)" + System.lineSeparator() +
                "503-245-3333   765-389-4444   2/27/19, 8:56 AM       2/27/19, 10:27 AM      5460           " + System.lineSeparator() +
                "503-245-2345   765-389-1273   2/27/20, 8:56 AM       2/27/20, 10:27 AM      5460           " + System.lineSeparator() +
                System.lineSeparator() +
                "----------------------------------------------------------", out.strip());
    }

    @Test
    public void test9_getPhoneCallWithinDateRange() {
        // search for user's calls
        MainMethodResult result = invokeMain(Project4.class, HOST_OPTION, HOSTNAME, PORT_OPTION, PORT, "Dave", "02/27/2019" ,"8:56", "am", "02/25/2020", "10:27", "am");
        String out = result.getTextWrittenToStandardOut();
        assertEquals("----------------------------------------------------------" + System.lineSeparator() +
                "Phone Bill for customer 'Dave':" + System.lineSeparator() +
                System.lineSeparator() +
                "Following are the phone calls in the phone bill:" + System.lineSeparator() +
                System.lineSeparator() +
                "Caller         Callee         Start Time             End Time               Duration (in seconds)" + System.lineSeparator() +
                "503-245-3333   765-389-4444   2/27/19, 8:56 AM       2/27/19, 10:27 AM      5460           " + System.lineSeparator() +
                System.lineSeparator() +
                "----------------------------------------------------------", out.strip());
    }

    @Test
    public void test10_addNewPhoneBillAndPrint() {
        MainMethodResult result = invokeMain(Project4.class, HOST_OPTION, HOSTNAME, PORT_OPTION, PORT, PRINT_OPTION, "Tommie", "503-245-2345", "765-389-1273", "02/27/2020" ,"8:56", "am", "02/27/2020", "10:27", "am");

        String out = result.getTextWrittenToStandardOut();

        assertEquals("Phone Call added to the phone bill for Tommie", out.strip());
    }
}