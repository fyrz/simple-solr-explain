package org.fyrz.solr.explain.parser.sequences;

import org.fyrz.solr.explain.model.DocumentMatch;
import org.fyrz.solr.explain.parser.types.ExplainElement;
import org.fyrz.solr.explain.parser.types.ExplainElementType;

import java.util.List;

public class DocumentMatchSequence extends ExplainElementSequence<DocumentMatch> {

  private final ExplainElementType[] sequence = {
      ExplainElementType.NUMBER,
      ExplainElementType.ASSIGNMENT,
      ExplainElementType.MATCH,
  };

  private final ExplainElementType[] prohibitedElements = {
      ExplainElementType.WEIGHT
  };

  @Override
  public boolean isDescribedBy(List<ExplainElement> explainElements) {
    return checkSequence(explainElements, sequence, prohibitedElements);
  }

  @Override
  public DocumentMatch interprets(List<ExplainElement> explainElements, DocumentMatch documentMatch) {
    Double documentScore = Double.valueOf(explainElements.get(0).getData());
    documentMatch.setScore(documentScore);

    // check for max plus tie breaker
    if (explainElements.size() > 5 &&
        explainElements.get(3).getType().equals(ExplainElementType.MAXPLUS) &&
        explainElements.get(4).getType().equals(ExplainElementType.NUMBER)) {
        // processing logic TODO
    }
    return documentMatch;
  }
}
