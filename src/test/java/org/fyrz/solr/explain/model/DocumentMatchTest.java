package org.fyrz.solr.explain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentMatchTest {

  @Test
  public void documentId(){
    DocumentMatch documentMatch = new DocumentMatch("123");
    assertThat(documentMatch.getDocumentId()).isEqualTo("123");
  }

  @Test
  public void fieldMatches() {
    DocumentMatch documentMatch = new DocumentMatch("124");
    assertThat(documentMatch.getFieldMatches().size()).isEqualTo(0);
    documentMatch.addFieldMatch(new FieldMatch());
    assertThat(documentMatch.getFieldMatches().size()).isEqualTo(1);
  }

  @Test
  public void score() {
    DocumentMatch documentMatch = new DocumentMatch("125");
    assertThat(documentMatch.getScore()).isEqualTo(-1);
    documentMatch.setScore(2.0);
    assertThat(documentMatch.getScore()).isEqualTo(2.0);
  }
}
