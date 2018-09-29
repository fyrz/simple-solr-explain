package org.solr.contrib.explain;

import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;

//@org.apache.lucene.util.LuceneTestCase.SuppressCodecs({"Lucene3x", "Lucene40"})
public abstract class AbstractSolrExplainTest extends SolrTestCaseJ4 {

  protected EmbeddedSolrServer server;

  @BeforeClass
  public static void initSolrCore() throws Exception {
    SolrTestCaseJ4.initCore("solr/collection1/solrconfig.xml", "solr/collection1/conf/schema.xml");
  }

  @Before
  public void initServer() throws IOException, SolrServerException {
    server = new EmbeddedSolrServer(h.getCoreContainer(), h.getCore().getName());
    setupTestBase();
    server.commit();
  }

  @After
  public void cleanupIndex() {
    super.clearIndex();
  }

  private void setupTestBase() throws IOException, SolrServerException {
    writeSolrInputDocumentToIndex("1", "Quick Fox", "The quick brown fox jumped the fence.");
    writeSolrInputDocumentToIndex("2", "Slow Bear", "The slow bear went to grab some fish.");
    writeSolrInputDocumentToIndex("3", "Early bird", "A early bird catches everything before the late one.");
    writeSolrInputDocumentToIndex("4", "Early quick eagle", "The Old English The Battle of Maldon refers to the Earn or Sea Eagle as a carrion eater in 10th century Essex.");
    writeSolrInputDocumentToIndex("5", "Hungry rabbit", "They came across a bunny rabbit who looked hungry.");
  }

  private void writeSolrInputDocumentToIndex(final String id, final String title, final String description) throws IOException, SolrServerException {
    SolrInputDocument inputDocument = new SolrInputDocument();
    inputDocument.addField("id", id);
    inputDocument.addField("title", title);
    inputDocument.addField("description", description);
    server.add(inputDocument);
  }
}