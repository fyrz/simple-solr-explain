package org.fyrz.solr.explain.model;

import java.util.ArrayList;
import java.util.List;

public class FieldMatch implements Comparable<FieldMatch> {

  private  Double score;

  private  String matchDescription;

  private  String similarityMethod;

  private final transient List<String> details = new ArrayList<>();

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
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    FieldMatch that = (FieldMatch) o;

    if (matchDescription != null ? !matchDescription.equals(that.matchDescription)
        : that.matchDescription != null) {
      return false;
    }
    if (score != null ? !score.equals(that.score) : that.score != null) {
      return false;
    }
    if (similarityMethod != null ? !similarityMethod.equals(that.similarityMethod)
        : that.similarityMethod != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    int result = score != null ? score.hashCode() : 0;
    result = 31 * result + (matchDescription != null ? matchDescription.hashCode() : 0);
    result = 31 * result + (similarityMethod != null ? similarityMethod.hashCode() : 0);
    return result;
  }
}
