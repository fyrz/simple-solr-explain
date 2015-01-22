package org.solr.contrib.plugin.response;

import java.io.IOException;
import java.io.Writer;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.JSONResponseWriter;
import org.apache.solr.response.SolrQueryResponse;
import org.solr.contrib.explain.SimpleSolrExplain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleExplainJsonResponseWriter extends JSONResponseWriter {
  protected static Logger log = LoggerFactory.getLogger(SimpleExplainJsonResponseWriter.class);

  @Override
  public void write(final Writer writer, final SolrQueryRequest req, final SolrQueryResponse rsp)
      throws IOException {
    NamedList namedList = rsp.getValues();
    try {
      SimpleOrderedMap debugMap = (SimpleOrderedMap) namedList.get("debug");
      if (debugMap != null) {
        @SuppressWarnings("unchecked")
        SimpleOrderedMap<String> explainMap = (SimpleOrderedMap<String>) debugMap.get("explain");
        rsp.add("simpleExplain", SimpleSolrExplain.simpleEDismaxJson(explainMap, true));
      }
    } catch (ClassCastException e) {
    }
    super.write(writer, req, rsp);
  }
}
