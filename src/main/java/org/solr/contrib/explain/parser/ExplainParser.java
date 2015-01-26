package org.solr.contrib.explain.parser;

import org.apache.solr.common.util.SimpleOrderedMap;
import org.solr.contrib.explain.model.DocumentMatch;
import org.solr.contrib.explain.model.FieldMatch;
import org.solr.contrib.explain.model.SimpleExplanation;
import org.solr.contrib.explain.parser.sequences.DocumentMatchSequence;
import org.solr.contrib.explain.parser.sequences.FieldMatchSequence;
import org.solr.contrib.explain.parser.types.ExplainElement;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ExplainParser {

  // Parse explain line
  private static final ExplainLineParser explainLineParser = new ExplainLineParser();

  // Possible sequence matcher
  private static DocumentMatchSequence documentMatchSequence = new DocumentMatchSequence();
  private static FieldMatchSequence fieldMatchSequence = new FieldMatchSequence();

  /**
   * Parse parsedQuery & explain string to SimpleExplanation structure.
   *
   * @param parsedQuery Solr parsedQueryString
   * @param explainMap Solr SimpleOrderedMap.
   *
   * @return SimpleExplanation instance.
   */
  public static SimpleExplanation parse(final String parsedQuery, final SimpleOrderedMap<String> explainMap) {
    // Result object
    final SimpleExplanation simpleExplanation = new SimpleExplanation();
    // State variables
    boolean documentOpen = false;
    DocumentMatch documentMatch = null;
    FieldMatch fieldMatch = null;

    // Process parsedQuery String
    simpleExplanation.setSearchFields(
        ParsedQueryParser.parseQuery(parsedQuery));

    // Process explain string line by line
    for (final Map.Entry<String, String> entry : explainMap) {
      String[] lines = entry.getValue().split("\n");
      for (String line : lines) {
        List<ExplainElement> explainElements = explainLineParser.parseSingleLine(line);

        // Check if explain elements describe Document Match
        if (documentMatchSequence.isDescribedBy(explainElements)) {
          documentOpen = true;
          documentMatch = new DocumentMatch(entry.getKey());
          simpleExplanation.addDocumentMatch(entry.getKey(), documentMatch);
          documentMatchSequence.interprets(explainElements, documentMatch);
        }

        // Check if explain elements describe FieldMatch
        else if (fieldMatchSequence.isDescribedBy(explainElements) && documentOpen) {
          if (documentMatch != null) {
            fieldMatch = new FieldMatch();
            documentMatch.addFieldMatch(fieldMatch);
            fieldMatchSequence.interprets(explainElements, fieldMatch);
          }
        }

        // Process non determined sequences
        else {
          if (fieldMatch != null) {
            fieldMatch.addDetail(line);
          }
        }
      }
    }
    postProcess(simpleExplanation);
    return simpleExplanation;
  }

  private static void postProcess(SimpleExplanation simpleExplanation) {
    // Sort all field matches in reverse order per document match.
    for (Map.Entry<String, DocumentMatch> entry : simpleExplanation.getDocumentMatches()) {
      Collections.sort(entry.getValue().getFieldMatches());
      Collections.reverse(entry.getValue().getFieldMatches());
    }
  }

}
