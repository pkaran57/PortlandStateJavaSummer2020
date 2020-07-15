package edu.pdx.cs410J.pkaran.domian.text;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import edu.pdx.cs410J.pkaran.domian.PhoneCall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TextDumper implements PhoneBillDumper {

    private final static String DELIMITER = "|";

    private final Path outputDirectoryPath;

    public TextDumper() {
        this(Paths.get(System.getProperty("user.dir")));
    }

    public TextDumper(Path outputDirectoryPath) {
        this.outputDirectoryPath = outputDirectoryPath;
    }

    @Override
    public void dump(AbstractPhoneBill abstractPhoneBill) throws IOException {
        List<String> lines = new LinkedList<>();
        lines.add(abstractPhoneBill.getCustomer());

        Collection<PhoneCall> phoneCalls = abstractPhoneBill.getPhoneCalls();

        if (phoneCalls != null && !phoneCalls.isEmpty()) {
            List<String> phoneCallLines = phoneCalls.stream()
                                                    .map(phoneCall -> phoneCall.getStringRepresentation(DELIMITER))
                                                    .collect(Collectors.toList());

            lines.addAll(phoneCallLines);
        }

        Path filePath = Paths.get(String.valueOf(outputDirectoryPath), String.format("%s-PhoneBill.txt", abstractPhoneBill.getCustomer()));

        Files.write(filePath, lines);
    }
}
