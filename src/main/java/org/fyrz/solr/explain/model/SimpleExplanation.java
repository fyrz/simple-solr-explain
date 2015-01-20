package org.fyrz.solr.explain.model;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


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

  public String toJson(boolean prettyPrint) {
    Gson gson = new GsonBuilder().create();
    if (prettyPrint) {
      gson = new GsonBuilder().setPrettyPrinting().create();
    }
    StringBuilder jsonStr = new StringBuilder();
    jsonStr.append("[\n");
    boolean firstElement = true;
    for (Map.Entry<String, DocumentMatch> entry : getDocumentMatches()) {
      if (!firstElement) {
        jsonStr.append(",\n");
      }
      firstElement = false;
      jsonStr.append(gson.toJson(entry.getValue()));
    }
    jsonStr.append("\n]");
    return jsonStr.toString();
  }
}
