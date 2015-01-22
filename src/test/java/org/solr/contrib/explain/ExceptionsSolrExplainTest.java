package org.solr.contrib.explain;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.solr.contrib.explain.model.SimpleExplanation;

public class ExceptionsSolrExplainTest extends AbstractSolrExplainTest {
  @Test
  public void catchAllQuery() throws SolrServerException {
    SolrQuery query = new SolrQuery("*:*");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(5L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(5, simpleExplanation.getDocumentMatches().size());
  }

  @Test
  public void noMatchQuery() throws SolrServerException {
    SolrQuery query = new SolrQuery("unknownToken");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(0L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(0, simpleExplanation.getDocumentMatches().size());
  }
}
