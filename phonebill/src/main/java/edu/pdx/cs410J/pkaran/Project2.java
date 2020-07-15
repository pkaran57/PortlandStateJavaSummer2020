package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.pkaran.domian.PhoneBill;
import edu.pdx.cs410J.pkaran.domian.PhoneCall;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {

  static final String READ_ME = "\nStudent name: Karan Patel, Project 2: Storing Your Phone Bill in a Text File\n\n" +
                                "This application generates a Phone Bill with a Phone Call and optionally prints information about the Phone Call.\n" +
                                "Phone bills can also be stored and loaded from a (text) file.\n\n" +
          "Usage: java edu.pdx.cs410J.pkaran.Project1 [options] <args>\n" +
          "\targs are (in this order):\n" +
            "\t\tcustomer - Person whose phone bill we’re modeling\n" +
            "\t\tcallerNumber - Phone number of caller\n" +
            "\t\tcalleeNumber - Phone number of person who was called\n" +
            "\t\tstart - Date and time call began (24-hour time)\n" +
            "\t\tend - Date and time call ended (24-hour time)\n" +
          "\toptions are (options may appear in any order):\n" +
            "\t\t-textFile file Where to read/write the phone bill\n" +
            "\t\t-print Prints a description of the new phone call\n" +
            "\t\t-README Prints a README for this project and exits\n" +
          "Date and time should be in the format: mm/dd/yyyy hh:mm";


  static final String READ_ME_OPTION = "-README";
  static final String PRINT_OPTION = "-print";
  static final String TEXT_FILE_OPTION = "-textFile";
  static final List<String> PROGRAM_OPTIONS = List.of(READ_ME_OPTION, PRINT_OPTION, TEXT_FILE_OPTION);

  /**
   * main method, entry point for the app
   * @param commandLineArgs command line arguments entered by the caller
   */
  public static void main(String[] commandLineArgs) {
    List<String> commandLineArguments = Arrays.asList(commandLineArgs);

    try {
      if (commandLineArguments.isEmpty()) {
        throw new IllegalArgumentException("Missing command line arguments");
      }

      if (commandLineArguments.contains(READ_ME_OPTION)) {
        System.out.print(READ_ME);
      } else {
        boolean printOptionFlag = commandLineArguments.contains(PRINT_OPTION);

        // command line arguments minus the 2 options
        List<String> programArguments = commandLineArguments.stream()
                                                            .filter(arg -> !PROGRAM_OPTIONS.contains(arg))
                                                            .collect(Collectors.toList());

        if (programArguments.size() != 7) {
          throw new IllegalArgumentException(String.format("Expected a total of 7 arguments of the form: [customer callerNumber calleeNumber start-date start-time end-date end-time] but got the following %d : %s", programArguments.size(), programArguments.toString().replace(",", "")));
        }

        // generate an instance of PhoneCall based on command line args
        PhoneCall phoneCall = PhoneCall.PhoneCallBuilder.aPhoneCall()
                                                        .withCaller(programArguments.get(1))
                                                        .withCallee(programArguments.get(2))
                                                        .withStartTime(programArguments.get(3) + " " + programArguments.get(4))
                                                        .withEndTime(programArguments.get(5) + " " + programArguments.get(6))
                                                        .build();

        // generate an instance of PhoneBill based on command line args and add PhoneCall to it
        PhoneBill<PhoneCall> phoneBill = PhoneBill.PhoneBillBuilder.aPhoneBill()
                                                        .withCustomerName(programArguments.get(0))
                                                        .withPhoneCalls(Arrays.asList(phoneCall))
                                                        .build();

        if (printOptionFlag) {
          System.out.print(phoneCall.toString());
        }
      }
    } catch (Exception ex) {
      System.err.print(ex.getMessage());
      System.exit(1);
    }
  }
}