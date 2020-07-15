package edu.pdx.cs410J.pkaran.domian.text;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import edu.pdx.cs410J.pkaran.domian.PhoneCall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates and dumps text representation of PhoneBill into a file
 */
public class TextDumper implements PhoneBillDumper {

    private final Path filePath;

    /**
     * @param filePath path of the file to text string representation of PhoneBill into
     */
    public TextDumper(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Generates and dumps text representation of PhoneBill into a file
     * @param abstractPhoneBill phone bill whose text representation needs to be dumped
     * @throws IOException
     */
    @Override
    public void dump(AbstractPhoneBill abstractPhoneBill) throws IOException {
        List<String> lines = new LinkedList<>();
        lines.add(abstractPhoneBill.getCustomer());

        Collection<PhoneCall> phoneCalls = abstractPhoneBill.getPhoneCalls();

        if (phoneCalls != null && !phoneCalls.isEmpty()) {
            List<String> phoneCallLines = phoneCalls.stream()
                                                    .map(PhoneCall::getStringRepresentation)
                                                    .collect(Collectors.toList());

            lines.addAll(phoneCallLines);
        }

        Files.write(filePath, lines);
    }
}
