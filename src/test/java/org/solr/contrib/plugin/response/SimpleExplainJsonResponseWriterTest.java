package org.solr.contrib.plugin.response;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.solr.SolrJettyTestBase;
import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.JettySolrRunner;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


@SolrTestCaseJ4.SuppressSSL
public class SimpleExplainJsonResponseWriterTest extends SolrJettyTestBase {
  private static JettySolrRunner jettySolrRunner;
  private SolrClient solrClient;

  @BeforeClass
  public static void beforeTest() throws Exception {
    URL url = SimpleExplainJsonResponseWriterTest.class.getResource("/solr");
    String path = Paths.get(url.toURI()).normalize().toString();
    jettySolrRunner = createJetty(
        path, "colection1/conf/solrconfig.xml",
        "collection1/conf/schema.xml",
        null, true, null);
  }

  @Before
  public void initTest() {
    solrClient = createNewSolrClient();
  }

  @Test
  public void testResponseWriter()
      throws IOException, SolrServerException {
    setupTestBase();
    retrieveHttpResult("fox");
  }

  private void setupTestBase() throws IOException, SolrServerException {
    writeSolrInputDocumentToIndex("1", "Quick Fox", "The quick brown fox jumped the fence.");
    writeSolrInputDocumentToIndex("2", "Slow Bear", "The slow bear went to grab some fish.");
    writeSolrInputDocumentToIndex("3", "Early bird", "A early bird catches everything before the late one.");
    writeSolrInputDocumentToIndex("4", "Early quick eagle", "The Old English The Battle of Maldon refers to the Earn or Sea Eagle as a carrion eater in 10th century Essex.");
    writeSolrInputDocumentToIndex("5", "Hungry rabbit", "They came across a bunny rabbit who looked hungry.");
  }

  private void writeSolrInputDocumentToIndex(final String id, final String title, final String description) throws IOException, SolrServerException {
    SolrInputDocument inputDocument = new SolrInputDocument();
    inputDocument.addField("id", id);
    inputDocument.addField("title", title);
    inputDocument.addField("description", description);
    solrClient.add(inputDocument);
  }

  private String retrieveHttpResult(String query)
      throws IOException {
    StringBuilder responseContent = new StringBuilder();

    List<NameValuePair> params = new LinkedList<>();
    params.add(new BasicNameValuePair("q", query));
    params.add(new BasicNameValuePair("debugQuery", "true"));
    params.add(new BasicNameValuePair("wt", "simpleExplainJson"));
    String paramString = URLEncodedUtils.format(params, "utf-8");

    HttpClient client = HttpClientBuilder.create().build();

    HttpGet request = new HttpGet(jettySolrRunner.getBaseUrl() + "/select?" + paramString);
    HttpResponse response = client.execute(request);

    BufferedReader rd = new BufferedReader
        (new InputStreamReader(response.getEntity().getContent()));

    String line;
    while ((line = rd.readLine()) != null) {
      responseContent.append(line);
    }
    return responseContent.toString();
  }
}
