package org.solr.contrib.explain;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.solr.contrib.explain.model.SimpleExplanation;

public class TermQuerySolrExplainTest extends AbstractSolrExplainTest {

  @Test
  public void termQuerySingleMatch() throws SolrServerException {
    SolrQuery query = new SolrQuery("brown");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(1L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(1, simpleExplanation.getDocumentMatches().size());
  }

  @Test
  public void termQueryMultiMatches() throws SolrServerException {
    SolrQuery query = new SolrQuery("quick");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(2L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(2, simpleExplanation.getDocumentMatches().size());
    assertNotNull(SimpleSolrExplain.simpleEDismaxJson(response, true));
  }

  @Test
  public void termQueryOrMatches() throws SolrServerException {
    SolrQuery query = new SolrQuery("+(quick OR brown)");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(2L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(2, simpleExplanation.getDocumentMatches().size());
    System.out.println(SimpleSolrExplain.simpleEDismaxJson(response, true));
  }
}
