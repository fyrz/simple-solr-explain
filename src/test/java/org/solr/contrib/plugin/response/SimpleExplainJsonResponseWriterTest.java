package org.solr.contrib.plugin.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.solr.SolrJettyTestBase;
import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.JettySolrRunner;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


@SolrTestCaseJ4.SuppressSSL
public class SimpleExplainJsonResponseWriterTest extends SolrJettyTestBase {
  private static JettySolrRunner jettySolrRunner;
  private SolrServer server;

  @BeforeClass
  public static void beforeTest() throws Exception {
    String path = SimpleExplainJsonResponseWriterTest.class.getResource("/").getPath();
    jettySolrRunner = createJetty(path + "solr", "/collection1/conf/solrconfig.xml", "/collection1/conf/schema.xml", null, true, null);
  }

  @Before
  public void initTest() {
    server = createNewSolrServer();
  }

  @Test
  public void testResponseWriter()
      throws IOException, SolrServerException {
    SolrInputDocument document = new SolrInputDocument();
    document.addField("id", 1);
    document.addField("title", "xyz");
    server.add(document);

    document = new SolrInputDocument();
    document.addField("id", 2);
    document.addField("title", "something else");
    document.addField("description", "xyz");
    server.add(document);

    document = new SolrInputDocument();
    document.addField("id", 3);
    document.addField("title", "something else xyz");
    document.addField("description", "xyz xyz");
    server.add(document);

    document = new SolrInputDocument();
    document.addField("id", 4);
    document.addField("title", "something else xyz");
    document.addField("description", "xyz synonym xyz");
    server.add(document);

    server.commit();
    String response = retrieveHttpResult("xyz");
  }

  private String retrieveHttpResult(String query)
      throws IOException {
    StringBuilder responseContent = new StringBuilder();

    List<NameValuePair> params = new LinkedList<NameValuePair>();
    params.add(new BasicNameValuePair("q", query));
    params.add(new BasicNameValuePair("debugQuery", "true"));
    params.add(new BasicNameValuePair("wt", "simpleExplainJson"));
    String paramString = URLEncodedUtils.format(params, "utf-8");

    HttpClient client = HttpClientBuilder.create().build();

    HttpGet request = new HttpGet(jettySolrRunner.getBaseUrl() + "/select?" + paramString);
    HttpResponse response = client.execute(request);

    BufferedReader rd = new BufferedReader
        (new InputStreamReader(response.getEntity().getContent()));

    String line = "";
    while ((line = rd.readLine()) != null) {
      responseContent.append(line);
    }
    return responseContent.toString();
  }
}
