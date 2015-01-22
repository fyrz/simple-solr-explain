package org.solr.contrib.explain;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.solr.contrib.explain.model.SimpleExplanation;

public class FuzzyQuerySolrExplainTest extends AbstractSolrExplainTest {
  @Test
  public void fuzzyQuery() throws SolrServerException {
    SolrQuery query = new SolrQuery("early~0.5");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(2L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(2, simpleExplanation.getDocumentMatches().size());
    System.out.println(SimpleSolrExplain.simpleEDismaxJson(response, true));
  }
}
