package org.solr.contrib.explain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldMatchTest {

  @Test
  public void matchDescription() {
    FieldMatch fieldMatch = new FieldMatch();
    fieldMatch.setMatchDescription("some description");
    assertThat(fieldMatch.getMatchDescription()).isEqualTo("some description");
  }

  @Test
  public void details() {
    FieldMatch fieldMatch = new FieldMatch();
    assertThat(fieldMatch.getDetails()).isEmpty();
    fieldMatch.addDetail("Detail");
    assertThat(fieldMatch.getDetails().size()).isEqualTo(1);
  }

  @Test
  public void compareTo() {
    FieldMatch fieldMatch = new FieldMatch();
    assertThat(fieldMatch.compareTo(fieldMatch)).isEqualTo(0);
  }

  @Test
  public void equals() {
    FieldMatch fieldMatch = new FieldMatch();
    assertThat(fieldMatch.equals(fieldMatch)).isTrue();
    assertThat(fieldMatch).isNotNull();
    // same score will be equal
    fieldMatch.setScore(2.5d);
    FieldMatch otherMatch = new FieldMatch();
    otherMatch.setScore(2.5d);
    assertThat(fieldMatch).isEqualTo(otherMatch);
    // different scores lead to false
    otherMatch.setScore(3.5d);
    assertThat(fieldMatch).isNotEqualTo(otherMatch);
    // even with same scores field matches differ with different similarity
    otherMatch.setScore(2.5d);
    fieldMatch.setSimilarityMethod("simple");
    otherMatch.setSimilarityMethod("advanced");
    assertThat(fieldMatch).isNotEqualTo(otherMatch);

    // but with same score and same similarity field matches are the same
    otherMatch.setSimilarityMethod("simple");
    assertThat(fieldMatch).isEqualTo(otherMatch);
    assertThat(otherMatch.getSimilarityMethod()).isEqualTo("simple");
  }

  @Test
  public void testHashCode() {
    FieldMatch fieldMatch = new FieldMatch();
    assertThat(fieldMatch.hashCode()).isEqualTo(fieldMatch.hashCode());
  }
}
