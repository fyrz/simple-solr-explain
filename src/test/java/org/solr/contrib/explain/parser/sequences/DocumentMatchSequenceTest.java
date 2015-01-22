package org.solr.contrib.explain.parser.sequences;

import org.junit.Test;
import org.solr.contrib.explain.parser.ExplainLineParser;
import org.solr.contrib.explain.parser.types.ExplainElement;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentMatchSequenceTest {

  private final ExplainLineParser explainLineParser = new ExplainLineParser();

  @Test
  public void isDescribedBy() {
    final String inLine = "0.5088134 = (MATCH) max plus 0.5 times others of:";
    List<ExplainElement> explainElements = explainLineParser.parseSingleLine(inLine);

    DocumentMatchSequence documentMatchSequence = new DocumentMatchSequence();
    assertThat(documentMatchSequence.isDescribedBy(explainElements)).isTrue();

    final String invalidLine = " 0.49997503 = (MATCH) weight(title:xyz^10.0 in 2) [DefaultSimilarity], result of:";
    explainElements = explainLineParser.parseSingleLine(invalidLine);
    assertThat(documentMatchSequence.isDescribedBy(explainElements)).isFalse();
  }
}
