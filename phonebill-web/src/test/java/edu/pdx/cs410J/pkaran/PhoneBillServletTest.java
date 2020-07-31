package edu.pdx.cs410J.pkaran;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static edu.pdx.cs410J.pkaran.PhoneBillServlet.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {

    @Test
    public void addPhoneBill() throws IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter pw = mock(PrintWriter.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");
        when(request.getParameter(CALLER_NUM_PARAM)).thenReturn("555-555-5556");
        when(request.getParameter(CALLEE_NUM_PARAM)).thenReturn("666-666-6667");
        when(request.getParameter(START_PARAM)).thenReturn("1/15/2020 8:39 am");
        when(request.getParameter(END_PARAM)).thenReturn("02/1/2020 1:03 pm");

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(pw).println("Phone Call added to the phone bill for Jake");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void andAndGetMultiplePhoneCallsForCustomer() throws IOException {

        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // add phone call #1
        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");
        when(request.getParameter(CALLER_NUM_PARAM)).thenReturn("555-555-5556");
        when(request.getParameter(CALLEE_NUM_PARAM)).thenReturn("666-666-6667");
        when(request.getParameter(START_PARAM)).thenReturn("1/15/2020 8:39 am");
        when(request.getParameter(END_PARAM)).thenReturn("02/1/2020 1:03 pm");

        PrintWriter pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(pw).println("Phone Call added to the phone bill for Jake");
        verify(response).setStatus(HttpServletResponse.SC_OK);

        // add phone call #2
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");
        when(request.getParameter(CALLER_NUM_PARAM)).thenReturn("555-777-5556");
        when(request.getParameter(CALLEE_NUM_PARAM)).thenReturn("666-888-6667");
        when(request.getParameter(START_PARAM)).thenReturn("10/15/2019 8:39 am");
        when(request.getParameter(END_PARAM)).thenReturn("1/16/2020 1:33 pm");

        pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(pw).println("Phone Call added to the phone bill for Jake");
        verify(response).setStatus(HttpServletResponse.SC_OK);

        // get all phone calls
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");

        pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        verify(pw).println("Jake" + System.lineSeparator() +
                "555-777-5556|666-888-6667|10/15/2019 8:39 AM|1/16/2020 1:33 PM" + System.lineSeparator() +
                "555-555-5556|666-666-6667|1/15/2020 8:39 AM|2/1/2020 1:3 PM");
        verify(response).setStatus(HttpServletResponse.SC_OK);

        // get phone calls between a certain date
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");
        when(request.getParameter(START_PARAM)).thenReturn("10/14/2018 8:39 am");
        when(request.getParameter(END_PARAM)).thenReturn("1/21/2020 4:45 am");

        pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        verify(pw).println("Jake" + System.lineSeparator() +
                "555-777-5556|666-888-6667|10/15/2019 8:39 AM|1/16/2020 1:33 PM");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void multiple_customers() throws IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // add phone call #1
        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");
        when(request.getParameter(CALLER_NUM_PARAM)).thenReturn("555-555-5556");
        when(request.getParameter(CALLEE_NUM_PARAM)).thenReturn("666-666-6667");
        when(request.getParameter(START_PARAM)).thenReturn("1/15/2020 8:39 am");
        when(request.getParameter(END_PARAM)).thenReturn("02/1/2020 1:03 pm");

        PrintWriter pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(pw).println("Phone Call added to the phone bill for Jake");
        verify(response).setStatus(HttpServletResponse.SC_OK);

        // add phone call #2
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jane");
        when(request.getParameter(CALLER_NUM_PARAM)).thenReturn("555-777-5556");
        when(request.getParameter(CALLEE_NUM_PARAM)).thenReturn("666-888-6667");
        when(request.getParameter(START_PARAM)).thenReturn("10/15/2019 8:39 am");
        when(request.getParameter(END_PARAM)).thenReturn("1/16/2020 1:33 pm");

        pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(pw).println("Phone Call added to the phone bill for Jane");
        verify(response).setStatus(HttpServletResponse.SC_OK);

        // get all phone calls for Jane
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jane");

        pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        verify(pw).println("Jane" + System.lineSeparator() +
                "555-777-5556|666-888-6667|10/15/2019 8:39 AM|1/16/2020 1:33 PM");
        verify(response).setStatus(HttpServletResponse.SC_OK);

        // get all phone calls for Jake
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");

        pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        verify(pw).println("Jake" + System.lineSeparator() +
                "555-555-5556|666-666-6667|1/15/2020 8:39 AM|2/1/2020 1:3 PM");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void noPhoneCallsFound() throws IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");

        PrintWriter pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        verify(pw).println("Did not find any phone calls for customer Jake");
        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void addPhoneBill_invalid_caller_param() throws IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter pw = mock(PrintWriter.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");
        when(request.getParameter(CALLER_NUM_PARAM)).thenReturn("555");
        when(request.getParameter(CALLEE_NUM_PARAM)).thenReturn("555-555-5556");
        when(request.getParameter(START_PARAM)).thenReturn("1/15/2020 8:39 am");
        when(request.getParameter(END_PARAM)).thenReturn("02/1/2020 1:03 pm");

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(pw).println("Caller phone number is invalid. Phone numbers should have the form nnn-nnn-nnnn where n is a number 0-9 but got 555");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void addPhoneBill_invalid_callee_param() throws IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter pw = mock(PrintWriter.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");
        when(request.getParameter(CALLER_NUM_PARAM)).thenReturn("555-555-5556");
        when(request.getParameter(CALLEE_NUM_PARAM)).thenReturn("555");
        when(request.getParameter(START_PARAM)).thenReturn("1/15/2020 8:39 am");
        when(request.getParameter(END_PARAM)).thenReturn("02/1/2020 1:03 pm");

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(pw).println("Callee phone number is invalid. Phone numbers should have the form nnn-nnn-nnnn where n is a number 0-9 but got 555");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void addPhoneBill_invalid_start_param() throws IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter pw = mock(PrintWriter.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");
        when(request.getParameter(CALLER_NUM_PARAM)).thenReturn("555-555-5556");
        when(request.getParameter(CALLEE_NUM_PARAM)).thenReturn("555-555-5556");
        when(request.getParameter(START_PARAM)).thenReturn("1/15/2020 8:39");
        when(request.getParameter(END_PARAM)).thenReturn("02/1/2020 1:03 pm");

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(pw).println("Start time is invalid. It should be in the following format: 'mm/dd/yyyy hh:mm am/pm' but got 1/15/2020 8:39");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void addPhoneBill_invalid_end_param() throws IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter pw = mock(PrintWriter.class);

        when(request.getParameter(CUSTOMER_PARAM)).thenReturn("Jake");
        when(request.getParameter(CALLER_NUM_PARAM)).thenReturn("555-555-5556");
        when(request.getParameter(CALLEE_NUM_PARAM)).thenReturn("555-555-5556");
        when(request.getParameter(START_PARAM)).thenReturn("1/15/2020 8:39 am");
        when(request.getParameter(END_PARAM)).thenReturn("02/1/2020 1:03");

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(pw).println("End time is invalid. It should be in the following format: 'mm/dd/yyyy hh:mm am/pm' but got 02/1/2020 1:03");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
