package org.fyrz.solr.explain.parser;

import org.apache.solr.common.util.SimpleOrderedMap;
import org.fyrz.solr.explain.model.DocumentMatch;
import org.fyrz.solr.explain.model.FieldMatch;
import org.fyrz.solr.explain.model.SimpleExplanation;
import org.fyrz.solr.explain.parser.sequences.DocumentMatchSequence;
import org.fyrz.solr.explain.parser.sequences.FieldMatchSequence;
import org.fyrz.solr.explain.parser.types.ExplainElement;

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
   * Parse explain string to SimpleExplanation structure.
   *
   * @param explainMap Solr SimpleOrderedMap.
   *
   * @return SimpleExplanation instance.
   */
  public static SimpleExplanation parse(final SimpleOrderedMap<String> explainMap) {
    // Result object
    final SimpleExplanation simpleExplanation = new SimpleExplanation();
    // State variables
    boolean documentOpen = false;
    DocumentMatch documentMatch = null;
    FieldMatch fieldMatch = null;
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
