package org.solr.contrib.explain;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.solr.contrib.explain.model.SimpleExplanation;
import org.solr.contrib.explain.parser.ExplainParser;

import java.util.Map;

public final class SimpleSolrExplain {

  static SimpleExplanation simpleEDismax(final QueryResponse queryResponse) {
    // Validate if query debugging is enabled
    Map<String, Object> debugMap = queryResponse.getDebugMap();
    if (debugMap == null || debugMap.isEmpty()) {
      throw new IllegalArgumentException("Query debugging must be enabled to use this method.");
    }

    // Check if EDismax parser was used.
    String queryParser = (String) debugMap.get("QParser");
    if (queryParser == null || !queryParser.equals("ExtendedDismaxQParser")) {
      throw new IllegalArgumentException("Processing is limited to process debug output of ExtendedDismaxQParser. QParser was:"+queryParser);
    }
    @SuppressWarnings("unchecked")
    SimpleExplanation simpleExplanation = ExplainParser.parse(
        (String)debugMap.get("parsedquery"),
        (SimpleOrderedMap<String>) debugMap.get("explain"));
    return simpleExplanation;
  }

  private static SimpleExplanation simpleEDismax(final String parsedQuery, final SimpleOrderedMap<String> explainMap) {
    return ExplainParser.parse(parsedQuery, explainMap);
  }

  static String simpleEDismaxJson(final QueryResponse queryResponse, final boolean prettyPrint) {
    SimpleExplanation simpleExplanation = simpleEDismax(queryResponse);
    return simpleExplanation.toJson(prettyPrint);
  }

  public static String simpleEDismaxJson(final String parsedQuery, final SimpleOrderedMap<String> explainMap, final boolean prettyPrint) {
    SimpleExplanation simpleExplanation = simpleEDismax(parsedQuery, explainMap);
    return simpleExplanation.toJson(prettyPrint);
  }
}
