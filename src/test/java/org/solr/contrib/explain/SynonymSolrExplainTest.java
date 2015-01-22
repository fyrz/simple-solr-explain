package org.solr.contrib.explain;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.solr.contrib.explain.model.SimpleExplanation;

public class SynonymSolrExplainTest extends AbstractSolrExplainTest {

  @Test
  public void synonymQuery() throws SolrServerException {
    // expands to bird and will match one document
    SolrQuery query = new SolrQuery("synonym");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(1L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(1, simpleExplanation.getDocumentMatches().size());
  }

  @Test
  public void multiSynonymQuery() throws SolrServerException {
    // expands to bird and battle and will match two documents
    SolrQuery query = new SolrQuery("othersynonym");
    query.setShowDebugInfo(true);

    QueryResponse response = server.query(query);
    assertEquals(2L, response.getResults().getNumFound());

    SimpleExplanation simpleExplanation = SimpleSolrExplain.simpleEDismax(response);
    assertEquals(2, simpleExplanation.getDocumentMatches().size());
  }
}
