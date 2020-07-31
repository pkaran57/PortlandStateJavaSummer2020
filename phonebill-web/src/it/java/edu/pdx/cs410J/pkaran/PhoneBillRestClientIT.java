package edu.pdx.cs410J.pkaran;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBillRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

//  @Test
//  public void test0RemoveAllDictionaryEntries() throws IOException {
//    PhoneBillRestClient client = newPhoneBillRestClient();
//    client.removeAllDictionaryEntries();
//  }
//
//  @Test
//  public void test1EmptyServerContainsNoDictionaryEntries() throws IOException {
//    PhoneBillRestClient client = newPhoneBillRestClient();
//    Map<String, String> dictionary = client.getAllDictionaryEntries();
//    assertThat(dictionary.size(), equalTo(0));
//  }
//
//  @Test
//  public void test2DefineOneWord() throws IOException {
//    PhoneBillRestClient client = newPhoneBillRestClient();
//    String testWord = "TEST WORD";
//    String testDefinition = "TEST DEFINITION";
//    client.addDictionaryEntry(testWord, testDefinition);
//
//    String definition = client.getDefinition(testWord);
//    assertThat(definition, equalTo(testDefinition));
//  }
//
//  @Test
//  public void test4MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
//    PhoneBillRestClient client = newPhoneBillRestClient();
//    HttpRequestHelper.Response response = client.postToMyURL(Map.of());
//    assertThat(response.getContent(), containsString(Messages.missingRequiredParameter("word")));
//    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
//  }

}
