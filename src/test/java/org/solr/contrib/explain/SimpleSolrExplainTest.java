package org.solr.contrib.explain;

import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.solr.contrib.explain.model.SimpleExplanation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class SimpleSolrExplainTest extends SolrTestCaseJ4 {

  private EmbeddedSolrServer server;

  @BeforeClass
  public static void initSolrCore() throws Exception {
    SolrTestCaseJ4.initCore("solr/collection1/conf/solrconfig.xml", "solr/collection1/conf/schema.xml");
  }

  @Before
  public void initServer() {
    server = new EmbeddedSolrServer(h.getCoreContainer(), h.getCore().getName());
  }

  @Test
  public void simpleTest() throws IOException, SolrServerException {
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

    SolrQuery query = new SolrQuery("xyz");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(4L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(4, simpleExplanation.getDocumentMatches().size());
    System.out.println(SimpleSolrExplain.simpleEDismaxJson(response, true));
  }

  @After
  public void cleanupIndex() {
    super.clearIndex();
  }
}