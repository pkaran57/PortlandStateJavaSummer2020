package edu.pdx.cs410J.pkaran.domian.text;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import edu.pdx.cs410J.pkaran.domian.PhoneBill;
import edu.pdx.cs410J.pkaran.domian.PhoneCall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Generates PhoneBill based on its text representation
 */
public class TextParser implements PhoneBillParser {

    private final Path filePath;

    /**
     * Path of file containing text representation of PhoneBill
     * @param filePath
     */
    public TextParser(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Generates PhoneBill based on its text representation
     * @return a PhoneBill object based on its text representation
     * @throws ParserException
     */
    @Override
    public AbstractPhoneBill parse() throws ParserException {
        String absoluteFilePath = filePath.toAbsolutePath().toString();

        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Unable to read lines from file (located at %s). Please ensure that the path to the file and file name is correct.",
                                                            absoluteFilePath),
                                            e);
        }

        lines = lines.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if(lines.isEmpty() || allLinesBlank(lines)) {
            throw new IllegalStateException("Did not find any data in file located at " + absoluteFilePath);
        }

        String customerName = null;
        List<PhoneCall> phoneCalls= new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).strip();

            if(i == 0) {
                if (line.isBlank()) {
                    throw new IllegalStateException(String.format("First line (containing customer name) in file %s cannot be blank", absoluteFilePath));
                }
                customerName = line;
                continue;
            }

            if(line.isBlank()) {
                continue;
            }

            phoneCalls.add(PhoneCall.generateFromStringRepresentation(line));
        }

        return PhoneBill.PhoneBillBuilder.aPhoneBill()
                .withCustomerName(customerName)
                .withPhoneCalls(phoneCalls)
                .build();
    }

    /**
     * Checks if all lines are blank
     * @param lines lines to check
     * @return true if all lines are blank, false otherwise
     */
    static boolean allLinesBlank(List<String> lines) {
        return lines.stream().allMatch(String::isBlank);
    }
}
