package org.solr.contrib.explain;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.solr.contrib.explain.model.SimpleExplanation;
import org.solr.contrib.explain.parser.ExplainParser;

import java.util.Map;

public class SimpleSolrExplain {

  public static SimpleExplanation simpleEDismax(QueryResponse queryResponse) {
    // Validate if query debugging is enabled
    Map<String, Object> debugMap = queryResponse.getDebugMap();
    if (debugMap == null || debugMap.isEmpty()) {
      throw new IllegalArgumentException("Query debugging must be enabled to use this method.");
    }

    // Check if EDismax parser was used.
    String queryParser = (String) debugMap.get("QParser");
    if (queryParser == null || !queryParser.equals("ExtendedDismaxQParser")) {
      throw new IllegalArgumentException("Processing is limited to process debug output of ExtendedDismaxQParser.");
    }
    @SuppressWarnings("unchecked")
    SimpleExplanation simpleExplanation = ExplainParser.parse((SimpleOrderedMap<String>) debugMap.get("explain"));
    return simpleExplanation;
  }

  public static SimpleExplanation simpleEDismax(SimpleOrderedMap<String> explainMap) {
    return ExplainParser.parse(explainMap);
  }

  public static String simpleEDismaxJson(QueryResponse queryResponse, boolean prettyPrint) {
    SimpleExplanation simpleExplanation = simpleEDismax(queryResponse);
    return simpleExplanation.toJson(prettyPrint);
  }

  public static String simpleEDismaxJson(SimpleOrderedMap<String> explainMap, boolean prettyPrint) {
    SimpleExplanation simpleExplanation = simpleEDismax(explainMap);
    return simpleExplanation.toJson(prettyPrint);
  }
}
