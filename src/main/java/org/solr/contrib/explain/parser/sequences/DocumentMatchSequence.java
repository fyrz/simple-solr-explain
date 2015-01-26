package org.solr.contrib.explain.parser.sequences;

import org.solr.contrib.explain.model.DocumentMatch;
import org.solr.contrib.explain.parser.types.ExplainElement;
import org.solr.contrib.explain.parser.types.ExplainElementType;

import java.util.List;

public class DocumentMatchSequence extends ExplainElementSequence<DocumentMatch> {

  private final ExplainElementType[] sequence = {
      ExplainElementType.NUMBER,
      ExplainElementType.ASSIGNMENT,
      ExplainElementType.MATCH,
  };

  private final ExplainElementType[] prohibitedElements = {
      ExplainElementType.FUNCTIONFIELD
  };

  @Override
  public boolean isDescribedBy(List<ExplainElement> explainElements) {
    return checkSequence(explainElements, sequence, prohibitedElements);
  }

  @Override
  public DocumentMatch interprets(List<ExplainElement> explainElements, DocumentMatch documentMatch) {
    Double documentScore = Double.valueOf(explainElements.get(0).getData());
    documentMatch.setScore(documentScore);
    return documentMatch;
  }
}
