package edu.pdx.cs410J.pkaran.domian.text;

import edu.pdx.cs410J.pkaran.domian.PhoneBill;
import edu.pdx.cs410J.pkaran.domian.PhoneCall;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TextDumperTest {

    File outputDirectory = new File("src/test/output");

    @Test
    public void dump_noPhoneCall() throws IOException {

        PhoneBill phoneBillForTom = PhoneBill.PhoneBillBuilder.aPhoneBill()
                .withCustomerName("Tom")
                .build();

        TextDumper textDumper = new TextDumper(outputDirectory.toPath());
        textDumper.dump(phoneBillForTom);

        File file = Paths.get(outputDirectory.toString(), "Tom-PhoneBill.txt").toFile();

        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());

        assertEquals(1, lines.size());
        assertEquals("Tom", lines.get(0));
    }

    @Test
    public void dump_onePhoneCall() throws IOException {
        PhoneCall phoneCall = PhoneCall.PhoneCallBuilder.aPhoneCall()
                .withCaller("555-555-5556")
                .withCallee("666-666-6667")
                .withStartTime("1/15/2020 19:39")
                .withEndTime("02/1/2020 1:03")
                .build();

        PhoneBill phoneBillForJake = PhoneBill.PhoneBillBuilder.aPhoneBill()
                                                                .withCustomerName("Jake")
                                                                .withPhoneCalls(List.of(phoneCall))
                                                                .build();

        TextDumper textDumper = new TextDumper(outputDirectory.toPath());
        textDumper.dump(phoneBillForJake);

        File file = Paths.get(outputDirectory.toString(), "Jake-PhoneBill.txt").toFile();

        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());

        assertEquals(2, lines.size());
        assertEquals("Jake", lines.get(0));
        assertEquals("555-555-5556|666-666-6667|1/15/2020 19:39|02/1/2020 1:03", lines.get(1));
    }

    @Test
    public void dump_multiplePhoneCalls() throws IOException {
        PhoneCall phoneCall1 = createPhoneCall("555-555-5556", "666-666-6667", "1/15/2020 19:39", "02/1/2020 1:03");
        PhoneCall phoneCall2 = createPhoneCall("777-555-5556", "666-777-6667", "1/15/2020 19:49", "02/1/2020 1:13");
        PhoneCall phoneCall3 = createPhoneCall("555-555-8888", "666-666-8888", "1/15/2020 19:59", "02/1/2020 1:23");

        PhoneBill phoneBillForJane = PhoneBill.PhoneBillBuilder.aPhoneBill()
                .withCustomerName("Jane")
                .withPhoneCalls(List.of(phoneCall1, phoneCall2, phoneCall3))
                .build();

        TextDumper textDumper = new TextDumper(outputDirectory.toPath());
        textDumper.dump(phoneBillForJane);

        File file = Paths.get(outputDirectory.toString(), "Jane-PhoneBill.txt").toFile();

        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());

        assertEquals(4, lines.size());
        assertEquals("Jane", lines.get(0));
        assertEquals("555-555-5556|666-666-6667|1/15/2020 19:39|02/1/2020 1:03", lines.get(1));
        assertEquals("777-555-5556|666-777-6667|1/15/2020 19:49|02/1/2020 1:13", lines.get(2));
        assertEquals("555-555-8888|666-666-8888|1/15/2020 19:59|02/1/2020 1:23", lines.get(3));
    }

    private static PhoneCall createPhoneCall(String caller, String callee, String startTime, String endTime) {
        return PhoneCall.PhoneCallBuilder.aPhoneCall()
                .withCaller(caller)
                .withCallee(callee)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .build();
    }
}