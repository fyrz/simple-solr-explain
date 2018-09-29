package org.solr.contrib.explain.parser.types;

public enum ExplainElementType {
  // matches every score
  NUMBER("[-]?[0-9]*\\.?[0-9]+"),
  // MAXPLUS - used in context with MaxDisjunction
  MAXPLUS("max plus"),
  // assignment operator
  ASSIGNMENT("="),
  // matches are denoted using this literal
  //MATCH("\\(MATCH\\)"),
  MATCH("(([m][a][x])|([s][u][m]) of:)|(\\*:\\*)"),
  // similarity method
  SIMILARITY("\\[.*\\]"),
  //weight(title:xyz^10.0 in 2)
  FUNCTIONFIELD("[a-zA-Z]+\\([a-z_A-Z]+:.+\\)"),
  // = idf(docFreq=3, maxDocs=4)
  IDF("idf\\(.*\\)"),
  // unknown tokens are matched with this
  UNKNOWN("\\S+"),
  // indent match
  INDENT("^[ ]+"),
  // matches whitespaces
  WHITESPACE("[ \t\f\r\n]+");

  private final String pattern;

  ExplainElementType(String pattern) {
    this.pattern = pattern;
  }

  public String getPattern() {
    return pattern;
  }
}
