package org.solr.contrib.explain;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solr.contrib.explain.model.SimpleExplanation;

public class TermQuerySolrExplainTest extends AbstractSolrExplainTest {
  protected static Logger log = LoggerFactory.getLogger(TermQuerySolrExplainTest.class);

  @Test
  public void termQuerySingleMatch() throws SolrServerException, IOException {
    SolrQuery query = new SolrQuery("brown");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(1L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);

    assertEquals(1, simpleExplanation.getDocumentMatches().size());
  }

  @Test
  public void termQueryMultiMatches() throws SolrServerException, IOException {
    SolrQuery query = new SolrQuery("quick");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(2L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(2, simpleExplanation.getDocumentMatches().size());
    assertNotNull(SimpleSolrExplain.simpleEDismaxJson(response, true));
  }

  @Test
  public void termQueryOrMatches() throws SolrServerException, IOException {
    SolrQuery query = new SolrQuery("+(quick OR brown)");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(2L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(2, simpleExplanation.getDocumentMatches().size());
  }
}
