package org.solr.contrib.explain.parser;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParsedQueryParserTest {
  @Test
  public void parsedTermQuery() {
    String parsedQuery = "(+DisjunctionMaxQuery((description:bird^0.2 | title:bird^10.0)~0.5))/no_coord";
    List<String> searchFields = ParsedQueryParser.parseQuery(parsedQuery);
    assertThat(searchFields)
        .contains("description")
        .contains("title");
  }

  @Test
  public void parsedFuzzyQuery() {
    String parsedQuery = "(+DisjunctionMaxQuery((description:early~2^0.2 | title:early~2^10.0)~0.5))/no_coord";
    List<String> searchFields = ParsedQueryParser.parseQuery(parsedQuery);
    assertThat(searchFields)
        .contains("description")
        .contains("title");
  }
}
