package org.solr.contrib.explain.parser.sequences;

import org.junit.Test;
import org.solr.contrib.explain.parser.types.ExplainElement;
import org.solr.contrib.explain.parser.types.ExplainElementType;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExplainElementSequenceTest {

  @Test
  public void checkProhibitedElements() {
    TestSequence sequence = new TestSequence();
    List<ExplainElement> explainElements = new ArrayList<>();
    explainElements.add(new ExplainElement(ExplainElementType.NUMBER, "123"));
    // Sequence of explain elements describes a TestSequence
    assertThat(sequence.isDescribedBy(explainElements)).isTrue();
    // Add prohibited element to list
    explainElements.add(new ExplainElement(ExplainElementType.FUNCTIONFIELD, "weight"));
    // Sequence describes no instance of TestSequence
    assertThat(sequence.isDescribedBy(explainElements)).isFalse();
  }

  private class TestSequence extends ExplainElementSequence<Object> {

    private final ExplainElementType[] sequence = {
        ExplainElementType.NUMBER
    };

    private final ExplainElementType[] prohibitedElements = {
        ExplainElementType.FUNCTIONFIELD
    };

    @Override
    public boolean isDescribedBy(List<ExplainElement> explainElements) {
      return checkSequence(explainElements, sequence, prohibitedElements);
    }

    @Override
    public Object interprets(List<ExplainElement> explainElements, Object object) {
      return null;
    }
  }
}
