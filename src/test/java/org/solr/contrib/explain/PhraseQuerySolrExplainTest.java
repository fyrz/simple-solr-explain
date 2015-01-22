package org.solr.contrib.explain;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.solr.contrib.explain.model.SimpleExplanation;

public class PhraseQuerySolrExplainTest extends AbstractSolrExplainTest {

  @Test
     public void phraseQuery() throws SolrServerException {
    SolrQuery query = new SolrQuery("\"quick brown\"");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(1L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(1, simpleExplanation.getDocumentMatches().size());
  }
}
