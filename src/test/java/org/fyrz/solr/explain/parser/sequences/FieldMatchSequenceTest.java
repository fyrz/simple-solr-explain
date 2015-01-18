package org.fyrz.solr.explain.parser.sequences;

import org.fyrz.solr.explain.parser.ExplainLineParser;
import org.fyrz.solr.explain.parser.types.ExplainElement;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldMatchSequenceTest {

  private final ExplainLineParser explainLineParser = new ExplainLineParser();

  @Test
  public void isDescribedBy(){
    final String inLine = " 0.49997503 = (MATCH) weight(title:xyz^10.0 in 2) [DefaultSimilarity], result of:";
    List<ExplainElement> explainElements = explainLineParser.parseSingleLine(inLine);

    FieldMatchSequence fieldMatchSequence = new FieldMatchSequence();
    assertThat(fieldMatchSequence.isDescribedBy(explainElements)).isTrue();

    final String invalidLine = "0.5088134 = (MATCH) max plus 0.5 times others of:";
    explainElements = explainLineParser.parseSingleLine(invalidLine);
    assertThat(fieldMatchSequence.isDescribedBy(explainElements)).isFalse();
  }
}
