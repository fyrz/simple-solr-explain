package org.solr.contrib.explain.parser.sequences;

import org.solr.contrib.explain.model.FieldMatch;
import org.solr.contrib.explain.parser.types.ExplainElement;
import org.solr.contrib.explain.parser.types.ExplainElementType;

import java.util.List;

public class FieldMatchSequence extends ExplainElementSequence<FieldMatch> {

  private final ExplainElementType[] sequence = {
      ExplainElementType.INDENT,
      ExplainElementType.NUMBER,
      ExplainElementType.ASSIGNMENT,
      ExplainElementType.MATCH,
      ExplainElementType.FUNCTIONFIELD
      // Similarity is optional for Wildcard-Matches
      //ExplainElementType.SIMILARITY
  };

  private final ExplainElementType[] prohibitedElements = {
  };

  @Override
  public boolean isDescribedBy(List<ExplainElement> explainElements) {
    return checkSequence(explainElements, sequence, prohibitedElements);
  }

  @Override
  public FieldMatch interprets(List<ExplainElement> explainElements, FieldMatch fieldMatch) {
    fieldMatch.setScore(Double.valueOf(explainElements.get(1).getData()));
    fieldMatch.setMatchDescription(explainElements.get(4).getData());

    if (explainElements.size() >= 6 && explainElements.get(5).getType().equals(ExplainElementType.SIMILARITY)) {
      fieldMatch.setSimilarityMethod(explainElements.get(5).getData());
    }

    return fieldMatch;
  }
}
