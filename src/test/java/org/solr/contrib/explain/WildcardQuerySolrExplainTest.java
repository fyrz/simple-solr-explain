package org.solr.contrib.explain;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.solr.contrib.explain.model.SimpleExplanation;

public class WildcardQuerySolrExplainTest extends AbstractSolrExplainTest {

  @Test
  public void wildcardQuery() throws SolrServerException, IOException {
    SolrQuery query = new SolrQuery("quic*");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(2L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(2, simpleExplanation.getDocumentMatches().size());
  }
}
