package org.fyrz.solr.explain.parser.sequences;

import org.fyrz.solr.explain.model.FieldMatch;
import org.fyrz.solr.explain.parser.types.ExplainElement;

import java.util.List;

public class UnknownSequence extends ExplainElementSequence<FieldMatch> {

  @Override
  public boolean isDescribedBy(List<ExplainElement> explainElements) {
    return true;
  }

  @Override
  public FieldMatch interprets(List<ExplainElement> explainElements, FieldMatch object) {
    return null;
  }
}
