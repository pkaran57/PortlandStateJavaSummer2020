package edu.pdx.cs410J.pkaran;

import edu.pdx.cs410J.pkaran.phonebill.PhoneBillController;
import edu.pdx.cs410J.pkaran.phonebill.domian.PhoneCall;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import static edu.pdx.cs410J.pkaran.phonebill.domian.PhoneCall.isTimeStampValid;
import static edu.pdx.cs410J.pkaran.phonebill.domian.PhoneCall.parseTimeStamp;

public class PhoneBillServlet extends HttpServlet {

    private final static String CUSTOMER_PARAM = "customer";
    private final static String CALLER_NUM_PARAM = "callerNumber";
    private final static String CALLEE_NUM_PARAM = "calleeNumber";
    private final static String START_PARAM = "start";
    private final static String END_PARAM = "end";

    private final PhoneBillController phoneBillController = new PhoneBillController();

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException {

        final String customer = getParameter(CUSTOMER_PARAM, request);
        final String start = getParameter(START_PARAM, request);
        final String end = getParameter(END_PARAM, request);

        Date startDate = null, endDate = null;

        if(customer == null) {
            writeMessageToResponse(response, "Error: Customer name cannot be null or blank");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if(start != null) {
            if (isTimeStampValid(start)) {
                startDate = parseTimeStamp(start);
            } else {
                writeMessageToResponse(response, String.format("Start time is invalid. It should be in the following format: 'mm/dd/yyyy hh:mm am/pm' but got %s", start));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        if(end != null) {
            if (isTimeStampValid(end)) {
                endDate = parseTimeStamp(end);
            } else {
                writeMessageToResponse(response, String.format("End time is invalid. It should be in the following format: 'mm/dd/yyyy hh:mm am/pm' but got %s", end));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        String phoneCallsTextDump = phoneBillController.getPhoneCalls(customer, startDate, endDate);

        if (phoneCallsTextDump == null) {
            writeMessageToResponse(response, "Did not find any phone calls for customer " + customer);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            writeMessageToResponse(response, phoneCallsTextDump);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException {
        String customer = getParameter(CUSTOMER_PARAM, request);
        String callerNumber = getParameter(CALLER_NUM_PARAM, request);
        String calleeNumber = getParameter(CALLEE_NUM_PARAM, request);
        String start = getParameter(START_PARAM, request);
        String end = getParameter(END_PARAM, request);

        if(customer == null) {
            writeMessageToResponse(response, "Error: Customer name cannot be null or blank");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        PhoneCall phoneCall;
        try {
            phoneCall = PhoneCall.PhoneCallBuilder.aPhoneCall()
                    .withCaller(callerNumber)
                    .withCallee(calleeNumber)
                    .withStartTime(start)
                    .withEndTime(end)
                    .build();

            phoneBillController.addPhoneCall(customer, phoneCall);
        } catch (Exception e) {
            writeMessageToResponse(response, e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        phoneBillController.addPhoneCall(customer, phoneCall);

        writeMessageToResponse(response, "Phone Call added to the phone bill of " + customer);
        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

//    @VisibleForTesting
//    String getDefinition(String word) {
//        return this.dictionary.get(word);
//    }

    private void writeMessageToResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType( "text/plain" );

        PrintWriter pw = response.getWriter();

        if (message.endsWith(System.lineSeparator())) {
            pw.print(message);
        } else {
            pw.println(message);
        }

        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
