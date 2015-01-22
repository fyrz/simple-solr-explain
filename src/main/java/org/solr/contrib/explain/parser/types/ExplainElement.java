package org.solr.contrib.explain.parser.types;

public class ExplainElement {
  private ExplainElementType type;
  private String data;

  public ExplainElement(final ExplainElementType type, final String data) {
    this.type = type;
    this.data = data;
  }

  public ExplainElementType getType() {
    return type;
  }

  public String getData() {
    return data;
  }
}
