package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.pkaran.phonebill.domian.PhoneCall;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    private static final String HOST_OPTION = "-host";
    private static final String PORT_OPTION = "-port";
    private static final String SEARCH_OPTION = "-search";
    private static final String PRINT_OPTION = "-print";
    private static final String READ_ME_OPTION = "-README";

    private static final List<String> PROGRAM_OPTIONS_WITH_NO_ARGS = List.of(READ_ME_OPTION, PRINT_OPTION, SEARCH_OPTION);

    private static final String READ_ME = "usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>"  + System.lineSeparator() +
            "\targs are (in this order):"  + System.lineSeparator() +
            "\t\tcustomer Person whose phone bill we’re modeling"  + System.lineSeparator() +
            "\t\tcallerNumber Phone number of caller"  + System.lineSeparator() +
            "\t\tcalleeNumber Phone number of person who was called"  + System.lineSeparator() +
            "\t\tstart Date and time call began"  + System.lineSeparator() +
            "\t\tend Date and time call ended"  + System.lineSeparator() +
            "\toptions are (options may appear in any order):"  + System.lineSeparator() +
            "\t\t-host hostname Host computer on which the server runs"  + System.lineSeparator() +
            "\t\t-port port Port on which the server is listening"  + System.lineSeparator() +
            "\t\t-search Phone calls should be searched for"  + System.lineSeparator() +
            "\t\t-print Prints a description of the new phone call"  + System.lineSeparator() +
            "\t\t-README Prints a README for this project and exits" + System.lineSeparator() +
            System.lineSeparator()  + "If the -search option is provided, only the customer, start and end are required.";

    public static void main(String... args) {

        String message;
        List<String> commandLineArguments = Arrays.stream(args).map(String::trim).collect(Collectors.toList());

        try {
            // only output README if readme option specified
            if (commandLineArguments.contains(READ_ME_OPTION)) {
                message = READ_ME;
            } else {
                final boolean printOptionFlag = commandLineArguments.contains(PRINT_OPTION);
                final boolean searchOptionFlag = commandLineArguments.contains(SEARCH_OPTION);

                final String host = getOptionValue(commandLineArguments, HOST_OPTION);
                final int port = getPort(commandLineArguments);

                final List<String> programArguments = getProgramArguments(commandLineArguments);

                if (searchOptionFlag && (programArguments.size() != 7 && programArguments.size() != 9)) {
                    throw new IllegalArgumentException(String.format("Expected 7 or 9 arguments to be specified of the form: [customer callerNumber calleeNumber start-date start-time am/pm end-date end-time am/pm] where only customer, start and end are required but got the following %d : %s", programArguments.size(), programArguments.toString().replace(",", "")));
                }
                if (!searchOptionFlag && (programArguments.size() != 1 && programArguments.size() != 9)) {
                    throw new IllegalArgumentException(String.format("Expected a total of 1 or 9 arguments of the form: [customer callerNumber calleeNumber start-date start-time am/pm end-date end-time am/pm] (where only customer should be specified if only 1 arg is present) but got the following %d : %s", programArguments.size(), programArguments.toString().replace(",", "")));
                }

                PhoneBillRestClient client = new PhoneBillRestClient(host, port);

                // generate an instance of PhoneCall based on command line args
                String customer = programArguments.get(0);

                if(programArguments.size() == 1) {
                    message = client.searchPhoneCalls(customer);
                } else {
                    PhoneCall phoneCall = createPhoneCall(programArguments);

                    if (searchOptionFlag) {
                        message = client.searchPhoneCalls(programArguments.get(1),
                                phoneCall.getStartTimeString(),
                                phoneCall.getEndTimeString());
                    } else {
                        message = client.addPhoneCall(customer, phoneCall);
                    }
                }
            }
        } catch(Exception ex) {
            System.err.print(ex.getMessage());
            System.exit(1);
            return;
        }

        System.out.println(message);
        System.exit(0);
    }

    /**
     * Create a phone call based on program options passed on via command line arguments
     * @param programArguments arguments passed on to the program
     * @return a PhoneCall object created based on information in programArguments
     */
    private static PhoneCall createPhoneCall(List<String> programArguments) {
        if (programArguments.size() == 7) {
            return PhoneCall.PhoneCallBuilder.aPhoneCall()
                    .withStartTime(programArguments.get(1) + " " + programArguments.get(2) + " " + programArguments.get(3))
                    .withEndTime(programArguments.get(4) + " " + programArguments.get(5) + " " + programArguments.get(6))
                    .build();
        } else {
            return PhoneCall.PhoneCallBuilder.aPhoneCall()
                    .withCaller(programArguments.get(1))
                    .withCallee(programArguments.get(2))
                    .withStartTime(programArguments.get(3) + " " + programArguments.get(4) + " " + programArguments.get(5))
                    .withEndTime(programArguments.get(6) + " " + programArguments.get(7) + " " + programArguments.get(8))
                    .build();
        }
    }

    /**
     * Extracts program arguments from the command line arguments
     * @param commandLineArguments arguments passed on to the program
     * @return program arguments extracted from the command line arguments
     */
    private static List<String> getProgramArguments(List<String> commandLineArguments) {
        List<String> args = commandLineArguments.stream()
                .filter(arg -> !PROGRAM_OPTIONS_WITH_NO_ARGS.contains(arg))
                .collect(Collectors.toList());

        if (args.contains(HOST_OPTION)) {
            int textFileOptionIndex = args.indexOf(HOST_OPTION);
            args.remove(textFileOptionIndex + 1);
            args.remove(HOST_OPTION);
        }

        if (args.contains(PORT_OPTION)) {
            int prettyPrintFileOptionIndex = args.indexOf(PORT_OPTION);
            args.remove(prettyPrintFileOptionIndex + 1);
            args.remove(PORT_OPTION);
        }

        return args;
    }

    /**
     * Get port from command line args
     * @param commandLineArguments
     * @return port
     */
    private static int getPort(List<String> commandLineArguments) {
        final String portString = getOptionValue(commandLineArguments, PORT_OPTION);
        try {
            return Integer.parseInt( portString );
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Port \"" + portString + "\" must be an integer");
        }
    }

    /**
     * Looks up option value corresponding to the option flag
     * @param commandLineArguments arguments passed on to the program
     * @return option value corresponding to the option flag. If option flag not found, an exception is thrown
     */
    private static String getOptionValue(List<String> commandLineArguments, String optionFlag) {
        String optionValue = null;

        if (commandLineArguments.contains(optionFlag)) {
            int optionIndex = commandLineArguments.indexOf(optionFlag);
            try{
                optionValue = commandLineArguments.get(optionIndex + 1);
            } catch (IndexOutOfBoundsException ex) {
                throw new IllegalArgumentException(String.format("Did not find any value for the %s option. Please specify it and try again.", optionFlag), ex);
            }
        } else {
            throw new IllegalArgumentException(String.format("Did not find the '%s' option. Please specify it and try again.", optionFlag));
        }
        return optionValue;
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }
}