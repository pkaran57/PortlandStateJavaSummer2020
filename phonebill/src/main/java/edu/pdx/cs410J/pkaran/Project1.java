package edu.pdx.cs410J.pkaran;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  static final String READ_ME = "Read me!";

  static final String READ_ME_OPTION = "-README";
  static final String PRINT_OPTION = "-print";
  static final List<String> PROGRAM_OPTIONS = List.of(READ_ME_OPTION, PRINT_OPTION);

  public static void main(String[] commandLineArgs) {
    List<String> commandLineArguments = Arrays.asList(commandLineArgs);

    try {
      if (commandLineArguments.isEmpty()) {
        throw new IllegalArgumentException("Missing command line arguments");
      }

      if (commandLineArguments.contains(READ_ME_OPTION)) {
        System.out.print(READ_ME);
      } else {
        boolean print = commandLineArguments.contains(PRINT_OPTION);

        List<String> programArguments = commandLineArguments.stream()
                                                            .filter(arg -> !PROGRAM_OPTIONS.contains(arg))
                                                            .collect(Collectors.toList());

        if (programArguments.size() != 7) {
          throw new IllegalArgumentException(String.format("Expected a total of 7 arguments of the form: [customer callerNumber calleeNumber start-date start-time end-date end-time] but got the following %d : %s", programArguments.size(), programArguments.toString().replace(",", "")));
        }

        PhoneCall phoneCall = PhoneCall.PhoneCallBuilder.aPhoneCall()
                                                        .withCaller(programArguments.get(1))
                                                        .withCallee(programArguments.get(2))
                                                        .withStartTime(programArguments.get(3) + " " + programArguments.get(4))
                                                        .withEndTime(programArguments.get(5) + " " + programArguments.get(6))
                                                        .build();

        PhoneBill<PhoneCall> phoneBill = PhoneBill.PhoneBillBuilder.aPhoneBill()
                                                        .withCustomerName(programArguments.get(0))
                                                        .withPhoneCalls(Arrays.asList(phoneCall))
                                                        .build();

        if (print) {
          System.out.print(phoneBill);
        }
      }
    } catch (Exception ex) {
      System.err.print(ex.getMessage());
      System.exit(1);
    }
  }
}