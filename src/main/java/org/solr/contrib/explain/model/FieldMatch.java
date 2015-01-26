package org.solr.contrib.explain.model;

import java.util.ArrayList;
import java.util.List;

public class FieldMatch implements Comparable<FieldMatch> {

  // details shall not be serialized
  private final transient List<String> details = new ArrayList<>();
  private Double score;
  private String matchDescription;

  // similarity method shall not be serialized
  private transient String similarityMethod;

  public void addDetail(final String detail) {
    details.add(detail);
  }

  public List<String> getDetails() {
    return new ArrayList<>(details);
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public String getMatchDescription() {
    return matchDescription;
  }

  public void setMatchDescription(String matchDescription) {
    this.matchDescription = matchDescription;
  }

  public String getSimilarityMethod() {
    return similarityMethod;
  }

  public void setSimilarityMethod(String similarityMethod) {
    this.similarityMethod = similarityMethod;
  }

  @Override
  public int compareTo(FieldMatch o) {
    if (equals(o)) {
      return 0;
    }
    return getScore().compareTo(o.getScore());
  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    FieldMatch that = (FieldMatch) o;

    return !(matchDescription != null ? !matchDescription.equals(that.matchDescription) : that.matchDescription != null) && !(score != null ? !score.equals(that.score) : that.score != null) && !(similarityMethod != null ? !similarityMethod.equals(that.similarityMethod) : that.similarityMethod != null);
  }

  @Override
  public int hashCode() {
    int result = score != null ? score.hashCode() : 0;
    result = 31 * result + (matchDescription != null ? matchDescription.hashCode() : 0);
    result = 31 * result + (similarityMethod != null ? similarityMethod.hashCode() : 0);
    return result;
  }
}
