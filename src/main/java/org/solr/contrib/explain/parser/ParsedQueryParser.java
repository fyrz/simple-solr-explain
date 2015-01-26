package org.solr.contrib.explain.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse Solr parsedQuery String into List of fields.
 */
public class ParsedQueryParser {

  private final static String regex = "([a-zA-Z_]+):";
  private static Pattern pattern = Pattern.compile(regex);

  /**
   * Parse parsedQuery String into List of fields.
   *
   * @param query Solr parsedQuery String
   *
   * @return List of field names.
   */
  public static List<String> parseQuery(final String query) {
    List<String> searchFields = new ArrayList<>();
    Set<String> searchFieldCandidates = new HashSet<>();
    Matcher matcher = pattern.matcher(query);
    while (matcher.find()) {
      searchFieldCandidates.add(matcher.group(1).trim());
    }
    searchFields.addAll(searchFieldCandidates);
    return searchFields;
  }
}
