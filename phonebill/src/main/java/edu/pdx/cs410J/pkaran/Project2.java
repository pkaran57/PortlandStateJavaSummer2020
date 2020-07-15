package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.pkaran.domian.PhoneBill;
import edu.pdx.cs410J.pkaran.domian.PhoneCall;
import edu.pdx.cs410J.pkaran.domian.text.TextDumper;
import edu.pdx.cs410J.pkaran.domian.text.TextParser;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main class for the CS410J Phone Bill Project 2
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
  static final List<String> PROGRAM_OPTIONS_WITH_NO_ARGS = List.of(READ_ME_OPTION, PRINT_OPTION);

  /**
   * main method, entry point for the app
   * @param commandLineArgs command line arguments entered by the caller
   */
  public static void main(String[] commandLineArgs) {
    List<String> commandLineArguments = Arrays.stream(commandLineArgs).map(String::trim).collect(Collectors.toList());

    try {
      // command line arguments validation
      if (commandLineArguments.isEmpty()) {
        throw new IllegalArgumentException("Missing command line arguments");
      }

      if(commandLineArguments.size() > 11) {
        throw new IllegalArgumentException("There should not be more than 11 arguments passed on to the program. Please check the program's readme by passing on the -README option");
      }

      checkOptionsOnlySpecifiedOnce(commandLineArguments);

      // only output README if readme option specified
      if (commandLineArguments.contains(READ_ME_OPTION)) {
        System.out.print(READ_ME);
      } else {
        final boolean printOptionFlag = commandLineArguments.contains(PRINT_OPTION);
        // get data file
        final File file = getPhoneBillTextFile(commandLineArguments);

        // command line arguments minus the 3 options
        List<String> programArguments = getProgramArguments(commandLineArguments);

        if (programArguments.size() != 7) {
          throw new IllegalArgumentException(String.format("Expected a total of 7 arguments of the form: [customer callerNumber calleeNumber start-date start-time end-date end-time] but got the following %d : %s", programArguments.size(), programArguments.toString().replace(",", "")));
        }

        // generate an instance of PhoneCall based on command line args
        PhoneCall phoneCall = createPhoneCall(programArguments);

        if (file != null && file.exists()) {
          TextParser parser = new TextParser(file.toPath());
          TextDumper dumper = new TextDumper(file.toPath());

          AbstractPhoneBill phoneBill = parser.parse();

          if(phoneBill.getCustomer() != programArguments.get(0)) {
            throw new IllegalArgumentException(String.format("Customer name in file (%s) does not match the customer name in the argument (%s)", phoneBill.getCustomer(), programArguments.get(0)));
          }

          phoneBill.addPhoneCall(phoneCall);

          dumper.dump(phoneBill);
        } else {
          // generate an instance of PhoneBill based on command line args and add PhoneCall to it
          PhoneBill<PhoneCall> phoneBill = PhoneBill.PhoneBillBuilder.aPhoneBill()
                  .withCustomerName(programArguments.get(0))
                  .withPhoneCalls(Arrays.asList(phoneCall))
                  .build();

          if (file != null && !file.exists()) {
            TextDumper dumper = new TextDumper(file.toPath());
            dumper.dump(phoneBill);
          }
        }

        if (printOptionFlag) {
          System.out.print(phoneCall.toString());
        }
      }
    } catch (Exception ex) {
      System.err.print(ex.getMessage());
      System.exit(1);
    }
  }

  /**
   * Looks up file in the command line argument and returns a File with the path to file
   * @param commandLineArguments arguments passed on to the program
   * @return File whose path is passed on in the command line argument
   */
  private static File getPhoneBillTextFile(List<String> commandLineArguments) {
    File file = null;

    if (commandLineArguments.contains(TEXT_FILE_OPTION)) {
      int textFileOptionIndex = commandLineArguments.indexOf(TEXT_FILE_OPTION);
      String filePath = null;
      try{
        filePath = commandLineArguments.get(textFileOptionIndex + 1);
      } catch (IndexOutOfBoundsException ex) {
        throw new IllegalArgumentException(String.format("Did not find the file after the %s option. Please specify it and try again.", TEXT_FILE_OPTION), ex);
      }

      file = new File(filePath);
    }

    return file;
  }

  /**
   * Create a phone call based on program options passed on via command line arguments
   * @param programArguments arguments passed on to the program
   * @return a PhoneCall object created based on information in programArguments
   */
  private static PhoneCall createPhoneCall(List<String> programArguments) {
    return PhoneCall.PhoneCallBuilder.aPhoneCall()
                                      .withCaller(programArguments.get(1))
                                      .withCallee(programArguments.get(2))
                                      .withStartTime(programArguments.get(3) + " " + programArguments.get(4))
                                      .withEndTime(programArguments.get(5) + " " + programArguments.get(6))
                                      .build();
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

    if (args.contains(TEXT_FILE_OPTION)) {
      int textFileOptionIndex = args.indexOf(TEXT_FILE_OPTION);
      args.remove(textFileOptionIndex + 1);
      args.remove(TEXT_FILE_OPTION);
    }

    return args;
  }

  /**
   * Checks to ensure no option is used more than once
   * @param commandLineArguments arguments passed on to the program
   */
  private static void checkOptionsOnlySpecifiedOnce(List<String> commandLineArguments) {
    for (String option: PROGRAM_OPTIONS) {
      if(Collections.frequency(commandLineArguments, option) > 1) {
        throw new IllegalArgumentException(String.format("Option '%s' found more than once", option));
      }
    }
  }
}