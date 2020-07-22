package edu.pdx.cs410J.pkaran.domian.text;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.pkaran.domian.PhoneCall;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TextParserTest {

    File phoneBillDataDirectory = new File("src/test/resources/edu/pdx/cs410J/pkaran");

    @Test
    public void parse_onlyCustomerName() throws ParserException {
        TextParser textParser = new TextParser(Paths.get(phoneBillDataDirectory.getAbsolutePath(), "Tom-PhoneBill.txt"));

        AbstractPhoneBill phoneBill = textParser.parse();

        assertEquals("Tom", phoneBill.getCustomer());
        assertTrue(phoneBill.getPhoneCalls().isEmpty());
    }

    @Test
    public void parse_customerNameOnePhoneBill() throws ParserException {
        TextParser textParser = new TextParser(Paths.get(phoneBillDataDirectory.getAbsolutePath(), "Jake-PhoneBill.txt"));

        AbstractPhoneBill phoneBill = textParser.parse();

        assertEquals("Jake", phoneBill.getCustomer());
        assertEquals(1, phoneBill.getPhoneCalls().size());

        PhoneCall phoneCall = (PhoneCall) phoneBill.getPhoneCalls().toArray()[0];

        assertEquals("555-555-5556", phoneCall.getCaller());
        assertEquals("666-666-6667", phoneCall.getCallee());
        assertEquals("1/15/20, 9:39 PM", phoneCall.getStartTimeString());
        assertEquals("2/1/20, 1:03 AM", phoneCall.getEndTimeString());
    }

    @Test
    public void parse_multiplePhoneBills() throws ParserException {
        TextParser textParser = new TextParser(Paths.get(phoneBillDataDirectory.getAbsolutePath(), "Jane-PhoneBill.txt"));

        AbstractPhoneBill phoneBill = textParser.parse();

        assertEquals("Jane", phoneBill.getCustomer());
        assertEquals(3, phoneBill.getPhoneCalls().size());
    }

    @Test
    public void allLinesBlank() {
        List<String> lines1 = Arrays.asList(" ", "");
        assertTrue(TextParser.allLinesBlank(lines1));

        List<String> lines2 = Arrays.asList("  ", "test", "");
        assertFalse(TextParser.allLinesBlank(lines2));
    }
}