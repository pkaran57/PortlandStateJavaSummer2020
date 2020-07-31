package edu.pdx.cs410J.pkaran;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.pkaran.phonebill.domian.PhoneCall;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static edu.pdx.cs410J.pkaran.PhoneBillServlet.*;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    public String searchPhoneCalls(String customer) throws IOException {
        Response response = get(this.url, Map.of(CUSTOMER_PARAM, customer));
        throwExceptionIfNotOkayHttpStatus(response);
        return response.getContent();
    }

    public String searchPhoneCalls(String customer, String start, String end) throws IOException {
        Response response = get(this.url, Map.of(CUSTOMER_PARAM, customer, START_PARAM, start, END_PARAM, end));
        throwExceptionIfNotOkayHttpStatus(response);
        return response.getContent();
    }

    public String addPhoneCall(String customer, PhoneCall phoneCall) throws IOException {
        Map<String, String> params = Map.of(CUSTOMER_PARAM, customer,
                                            CALLER_NUM_PARAM, phoneCall.getCaller(),
                                            CALLEE_NUM_PARAM, phoneCall.getCallee(),
                                            START_PARAM, phoneCall.getStartTimeString(),
                                            END_PARAM, phoneCall.getEndTimeString());

        Response response = postToMyURL(params);
        throwExceptionIfNotOkayHttpStatus(response);
        return response.getContent();
    }

    @VisibleForTesting
    Response postToMyURL(Map<String, String> dictionaryEntries) throws IOException {
      return post(this.url, dictionaryEntries);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
      int code = response.getCode();
      if (code != HTTP_OK) {
        throw new PhoneBillRestException(code, response.getContent());
      }
      return response;
    }

    @VisibleForTesting
    class PhoneBillRestException extends RuntimeException {
      PhoneBillRestException(int httpStatusCode, String message) {
        super("Got an HTTP Status Code of " + httpStatusCode + " with the following response from server: " + message);
      }
    }

}
