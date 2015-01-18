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
    return getScore().compareTo(o.getScore());
  }
}
