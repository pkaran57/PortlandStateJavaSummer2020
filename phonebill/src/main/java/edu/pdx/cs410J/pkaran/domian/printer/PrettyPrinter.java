package edu.pdx.cs410J.pkaran.domian.printer;

import edu.pdx.cs410J.PhoneBillDumper;
import edu.pdx.cs410J.pkaran.domian.PhoneBill;
import edu.pdx.cs410J.pkaran.domian.PhoneCall;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class PrettyPrinter<T extends PhoneBill<PhoneCall>> implements PhoneBillDumper<T> {

    private final File outputFile;

    public PrettyPrinter(File outputFile) {
        this.outputFile = outputFile;
    }

    public static final String FORMAT_STRING = "%-15s%-15s%-15s%-15s%-15s\n";

    @Override
    public void dump(T phoneBill) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(String.format("Phone Bill for customer '%s':\n\n", phoneBill.getCustomer()));

        List<PhoneCall> phoneCallsList = phoneBill.getPhoneCallsAsList();

        if (phoneCallsList.isEmpty()) {
            stringBuffer.append("No phone calls were found for the phone bill.");
        } else {
            stringBuffer.append("Following are the phone calls in the phone bill:\n");
            stringBuffer.append(getFormattedHeader());

            Collections.sort(phoneCallsList);
            phoneCallsList.forEach(phoneCall ->
                    stringBuffer.append(String.format(FORMAT_STRING,
                            phoneCall.getCaller(), phoneCall.getCallee(), phoneCall.getStartTimeString(), phoneCall.getEndTimeString(), phoneCall.getDuration())));
        }

        if (outputFile == null) {
            System.out.println(stringBuffer.toString());
        } else {
            Files.write(outputFile.toPath(), stringBuffer.toString().getBytes());
        }
    }

    private String getFormattedHeader() {
        return String.format(FORMAT_STRING, "Caller", "Callee", "Start Time", "End Time", "Duration");
    }
}
