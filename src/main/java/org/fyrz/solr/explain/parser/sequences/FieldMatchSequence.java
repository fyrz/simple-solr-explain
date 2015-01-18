package org.fyrz.solr.explain.parser.sequences;

import org.fyrz.solr.explain.model.FieldMatch;
import org.fyrz.solr.explain.parser.types.ExplainElement;
import org.fyrz.solr.explain.parser.types.ExplainElementType;

import java.util.List;

public class FieldMatchSequence extends ExplainElementSequence<FieldMatch> {

  private final ExplainElementType[] sequence = {
      ExplainElementType.INDENT,
      ExplainElementType.NUMBER,
      ExplainElementType.ASSIGNMENT,
      ExplainElementType.MATCH,
      ExplainElementType.WEIGHT,
      ExplainElementType.SIMILARITY
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
    fieldMatch.setSimilarityMethod(explainElements.get(5).getData());
    return fieldMatch;
  }
}
