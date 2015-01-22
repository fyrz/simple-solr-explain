package org.solr.contrib.explain;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.solr.contrib.explain.model.SimpleExplanation;

public class WildcardQuerySolrExplainTest extends AbstractSolrExplainTest {

  @Test
  public void wildcardQuery() throws SolrServerException {
    SolrQuery query = new SolrQuery("quic*");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(2L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(2, simpleExplanation.getDocumentMatches().size());
    System.out.println(SimpleSolrExplain.simpleEDismaxJson(response, true));
  }
}
