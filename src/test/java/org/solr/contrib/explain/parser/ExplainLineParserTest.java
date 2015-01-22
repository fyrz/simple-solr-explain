package org.solr.contrib.explain.parser;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExplainLineParserTest {

  private final ExplainLineParser explainLineParser = new ExplainLineParser();

  @Test
  public void parseDocumentMatchLine() {
    final String inLine = "0.5088134 = (MATCH) max plus 0.5 times others of:";
    assertThat(explainLineParser.parseSingleLine(inLine))
        .extracting("type")
        .extracting("name")
        .contains("NUMBER", "MATCH", "MAXPLUS")
        .doesNotContain("IDF", "ID", "INDENT");
  }

  @Test
  public void parseFieldMatchLine() {
    final String inLine = " 0.49997503 = (MATCH) weight(title:xyz^10.0 in 2) [DefaultSimilarity], result of:";
    assertThat(explainLineParser.parseSingleLine(inLine))
        .extracting("type")
        .extracting("name")
        .contains("INDENT", "NUMBER", "MATCH", "WEIGHT", "SIMILARITY")
        .doesNotContain("IDF");
  }

  @Test
  public void parseIdfLine() {
    final String inLine = "        1.0 = idf(docFreq=3, maxDocs=4)";
    assertThat(explainLineParser.parseSingleLine(inLine))
        .extracting("type")
        .extracting("name")
        .contains("INDENT", "NUMBER", "ASSIGNMENT", "IDF")
        .doesNotContain("MATCH", "WEIGHT");
  }
}
