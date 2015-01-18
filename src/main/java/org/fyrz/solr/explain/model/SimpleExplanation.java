package org.fyrz.solr.explain.model;

import java.util.*;

public class SimpleExplanation {
  SortedSet<Map.Entry<String, DocumentMatch>> documentMatches = new TreeSet<Map.Entry<String, DocumentMatch>>(
      new Comparator<Map.Entry<String, DocumentMatch>>() {
        @Override
        public int compare(Map.Entry<String, DocumentMatch> e1,
                           Map.Entry<String, DocumentMatch> e2) {
          return e2.getValue().getScore().compareTo(e1.getValue().getScore());
        }
      });


  public void addDocumentMatch(final String documentId, final DocumentMatch documentMatch) {
    Map.Entry<String, DocumentMatch> entry = new AbstractMap.SimpleEntry<String, DocumentMatch>(
        documentId, documentMatch);
    documentMatches.add(entry);
  }

  public SortedSet<Map.Entry<String, DocumentMatch>> getDocumentMatches() {
    return documentMatches;
  }
}
