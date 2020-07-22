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

    File outputDirectory = new File("src/test/resources/edu/pdx/cs410J/pkaran");

    @Test
    public void dump_noPhoneCall() throws IOException {
        File file = getFile(outputDirectory, "Tom-PhoneBill-out.txt");

        PhoneBill phoneBillForTom = PhoneBill.PhoneBillBuilder.aPhoneBill()
                .withCustomerName("Tom")
                .build();

        TextDumper textDumper = new TextDumper(file.toPath());
        textDumper.dump(phoneBillForTom);


        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());

        assertEquals(1, lines.size());
        assertEquals("Tom", lines.get(0));
    }

    @Test
    public void dump_onePhoneCall() throws IOException {
        File file = Paths.get(outputDirectory.toString(), "Jake-PhoneBill-out.txt").toFile();

        PhoneCall phoneCall = PhoneCall.PhoneCallBuilder.aPhoneCall()
                .withCaller("555-555-5556")
                .withCallee("666-666-6667")
                .withStartTime("1/15/2020 9:39 pm")
                .withEndTime("02/1/2020 1:03 am")
                .build();

        PhoneBill phoneBillForJake = PhoneBill.PhoneBillBuilder.aPhoneBill()
                                                                .withCustomerName("Jake")
                                                                .withPhoneCalls(List.of(phoneCall))
                                                                .build();

        TextDumper textDumper = new TextDumper(file.toPath());
        textDumper.dump(phoneBillForJake);

        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());

        assertEquals(2, lines.size());
        assertEquals("Jake", lines.get(0));
        assertEquals("555-555-5556|666-666-6667|1/15/2020 9:39 PM|2/1/2020 1:3 AM", lines.get(1));
    }

    @Test
    public void dump_multiplePhoneCalls() throws IOException {
        File file = Paths.get(outputDirectory.toString(), "Jane-PhoneBill-out.txt").toFile();

        PhoneCall phoneCall1 = createPhoneCall("555-555-5556", "666-666-6667", "1/15/2020 9:39 pm", "02/1/2020 1:03 am");
        PhoneCall phoneCall2 = createPhoneCall("777-555-5556", "666-777-6667", "1/15/2020 9:49 pm", "02/1/2020 1:13 am");
        PhoneCall phoneCall3 = createPhoneCall("555-555-8888", "666-666-8888", "1/15/2020 9:59 pmx", "02/1/2020 1:23 am");

        PhoneBill phoneBillForJane = PhoneBill.PhoneBillBuilder.aPhoneBill()
                .withCustomerName("Jane")
                .withPhoneCalls(List.of(phoneCall1, phoneCall2, phoneCall3))
                .build();

        TextDumper textDumper = new TextDumper(file.toPath());
        textDumper.dump(phoneBillForJane);

        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());

        assertEquals(4, lines.size());
        assertEquals("Jane", lines.get(0));
        assertEquals("555-555-5556|666-666-6667|1/15/2020 9:39 PM|2/1/2020 1:3 AM", lines.get(1));
        assertEquals("777-555-5556|666-777-6667|1/15/2020 9:49 PM|2/1/2020 1:13 AM", lines.get(2));
        assertEquals("555-555-8888|666-666-8888|1/15/2020 9:59 PM|2/1/2020 1:23 AM", lines.get(3));
    }

    private static PhoneCall createPhoneCall(String caller, String callee, String startTime, String endTime) {
        return PhoneCall.PhoneCallBuilder.aPhoneCall()
                .withCaller(caller)
                .withCallee(callee)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .build();
    }

    private static File getFile(File outputDirectory, String s) {
        return Paths.get(outputDirectory.toString(), s).toFile();
    }
}