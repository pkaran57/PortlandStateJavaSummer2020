package edu.pdx.cs410J.pkaran;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PhoneBillServlet extends HttpServlet {

    private final static String CUSTOMER_PARAM = "customer";
    private final static String CALLER_NUM_PARAM = "callerNumber";
    private final static String CALLEE_NUM_PARAM = "calleeNumber";
    private final static String START_PARAM = "start";
    private final static String END_PARAM = "end";



    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        String customer = getParameter(CUSTOMER_PARAM, request);
        String callerNumber = getParameter(CALLER_NUM_PARAM, request);
        String calleeNumber = getParameter(CALLEE_NUM_PARAM, request);
        String start = getParameter(START_PARAM, request);
        String end = getParameter(END_PARAM, request);



        response.setContentType( "text/plain" );
        if (word != null) {
            writeDefinition(word, response);

        } else {
            writeAllDictionaryEntries(response);
        }
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String word = getParameter(WORD_PARAMETER, request );
        if (word == null) {
            missingRequiredParameter(response, WORD_PARAMETER);
            return;
        }

        String definition = getParameter(DEFINITION_PARAMETER, request );
        if ( definition == null) {
            missingRequiredParameter( response, DEFINITION_PARAMETER );
            return;
        }

        this.dictionary.put(word, definition);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.definedWordAs(word, definition));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the definition of the given word to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    private void writeDefinition(String word, HttpServletResponse response) throws IOException {
        String definition = this.dictionary.get(word);

        if (definition == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();
            pw.println(Messages.formatDictionaryEntry(word, definition));

            pw.flush();

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Writes all of the dictionary entries to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        Messages.formatDictionaryEntries(pw, dictionary);

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
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

    @VisibleForTesting
    String getDefinition(String word) {
        return this.dictionary.get(word);
    }

}
