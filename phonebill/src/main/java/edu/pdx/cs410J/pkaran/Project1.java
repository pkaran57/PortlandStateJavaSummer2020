package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.pkaran.PhoneCall.PhoneCallBuilder;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
    PhoneCall call = PhoneCallBuilder.aPhoneCall().build();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    System.err.println("Missing command line arguments");
    for (String arg : args) {
      System.out.println(arg);
    }
    System.exit(1);
  }

}