package org.solr.contrib.explain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a document match.
 */
public class DocumentMatch {

  private final String documentId;

  private Double score = -1d;

  // By default the tie breaker is set to zero
  private Double tieBreaker = 0d;

  private final List<FieldMatch> fieldMatches = new ArrayList<>();

  /**
   * Construct document.
   *
   * @param documentId is the identifier of a document as configured in schema.xml.
   */
  public DocumentMatch(String documentId) {
    this.documentId = documentId;
  }

  /**
   * @return document documentId. This documentId is the same thing as configured in schema.xml
   * as unique key.
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Retrieve document score.
   *
   * @return double value representing the document score.
   */
  public Double getScore() {
    return score;
  }

  /**
   * Set the document score.
   *
   * @param score double value.
   */
  public void setScore(Double score) {
    this.score = score;
  }

  /**
   * Return tie breaker value.
   *
   * @return tieBreaker value.
   */
  public Double getTieBreaker() {
    return tieBreaker;
  }

  /**
   * Set tie breaker.
   *
   * @param tieBreaker double value.
   */
  public void setTieBreaker(Double tieBreaker) {
    this.tieBreaker = tieBreaker;
  }

  /**
   * Add a FieldMatch.
   *
   * @param fieldMatch instance of {@link org.solr.contrib.explain.model.FieldMatch}.
   */
  public void addFieldMatch(final FieldMatch fieldMatch) {
    fieldMatches.add(fieldMatch);
  }

  /**
   * Return all field matches for this document.
   *
   * @return Reverse Sorted List of field matches.
   */
  public List<FieldMatch> getFieldMatches() {
    return fieldMatches;
  }

}
