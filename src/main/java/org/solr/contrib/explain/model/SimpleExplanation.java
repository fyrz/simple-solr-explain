package org.solr.contrib.explain.model;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * SimpleExplanation of a Solr Explain String.
 */
public class SimpleExplanation {

  // List of fields where the search is performed in.
  List<String> searchFields = new ArrayList<>();

  // List of document matches associated to the search.
  SortedSet<Map.Entry<String, DocumentMatch>> documentMatches = new TreeSet<>(
      new Comparator<Map.Entry<String, DocumentMatch>>() {
        @Override
        public int compare(Map.Entry<String, DocumentMatch> e1,
                           Map.Entry<String, DocumentMatch> e2) {
          return e2.getValue().getScore().compareTo(e1.getValue().getScore());
        }
      });

  /**
   * Return List of search fields as String.
   *
   * @return List of search fields.
   */
  public List<String> getSearchFields() {
    return searchFields;
  }

  /**
   * Set list of search fields.
   *
   * @param searchFields List of search field Strings.
   */
  public void setSearchFields(List<String> searchFields) {
    this.searchFields = searchFields;
  }

  /**
   * Add a new document match to the SimpleExplanation instance.
   *
   * @param documentId of matching document.
   * @param documentMatch {@link org.solr.contrib.explain.model.DocumentMatch} instance.
   */
  public void addDocumentMatch(final String documentId, final DocumentMatch documentMatch) {
    Map.Entry<String, DocumentMatch> entry = new AbstractMap.SimpleEntry<>(
        documentId, documentMatch);
    documentMatches.add(entry);
  }

  /**
   * Return a SortedSet of document matches sorted by score in descending order.
   *
   * @return SortedSet of document matches.
   */
  public SortedSet<Map.Entry<String, DocumentMatch>> getDocumentMatches() {
    return documentMatches;
  }

  /**
   * Print SimpleExplanation as JSON
   *
   * @param prettyPrint boolean value indicating if formatting shall be applied.
   *
   * @return JSON string.
   */
  public String toJson(boolean prettyPrint) {
    Gson gson = new GsonBuilder().create();
    StringBuilder jsonStr = new StringBuilder();

    jsonStr.append("{\"searchFields\":");
    jsonStr.append(gson.toJson(searchFields));
    jsonStr.append(",\"documentMatches\":[");
    boolean firstElement = true;
    for (Map.Entry<String, DocumentMatch> entry : getDocumentMatches()) {
      if (!firstElement) {
        jsonStr.append(",");
      }
      firstElement = false;
      jsonStr.append(gson.toJson(entry.getValue()));
    }
    jsonStr.append("]");
    jsonStr.append("}");

    String retValue = jsonStr.toString();

    if (prettyPrint) {
      gson = new GsonBuilder().setPrettyPrinting().create();
      JsonParser jsonParser = new JsonParser();
      JsonElement jsonElement = jsonParser.parse(retValue);
      retValue = gson.toJson(jsonElement);
    }
    return retValue;
  }
}
