package org.solr.contrib.explain.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsedQueryParser {

  private final static String regex = "([a-zA-Z_]+):";
  private static Pattern pattern = Pattern.compile(regex);

  public static List<String> parseQuery(final String query) {
    List<String> searchFields = new ArrayList<>();
    Matcher matcher = pattern.matcher(query);
    while (matcher.find()) {
      searchFields.add(matcher.group(1));
    }
    return searchFields;
  }
}
