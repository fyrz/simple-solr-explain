package org.solr.contrib.explain.parser.sequences;

import org.solr.contrib.explain.model.FieldMatch;
import org.solr.contrib.explain.parser.types.ExplainElement;
import org.solr.contrib.explain.parser.types.ExplainElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldMatchSequence extends ExplainElementSequence<FieldMatch> {

  private final ExplainElementType[] sequence = {
      ExplainElementType.INDENT,
      ExplainElementType.NUMBER,
      ExplainElementType.ASSIGNMENT,
      //ExplainElementType.MATCH,
      ExplainElementType.FUNCTIONFIELD
      // Similarity is optional for Wildcard-Matches
      //ExplainElementType.SIMILARITY
  };

  private final ExplainElementType[] prohibitedElements = {
  };

  @Override
  public boolean isDescribedBy(List<ExplainElement> explainElements) {
    return checkSequence(explainElements, sequence, prohibitedElements);
  }

  @Override
  public FieldMatch interprets(List<ExplainElement> explainElements, FieldMatch fieldMatch) {
    fieldMatch.setScore(Double.valueOf(explainElements.get(1).getData()));
    fieldMatch.setMatchDescription(
        FieldMatchDescriptionParser.parseMatchDescription(explainElements.get(3).getData()));

    if (explainElements.size() >= 5 && explainElements.get(4).getType().equals(ExplainElementType.SIMILARITY)) {
      fieldMatch.setSimilarityMethod(explainElements.get(4).getData());
    }

    return fieldMatch;
  }

  private static class FieldMatchDescriptionParser {
    private final static String regex = ".*\\(([a-zA-Z_]+:.+)in.*";
    private static Pattern pattern = Pattern.compile(regex);

    /**
     * Parse matchDescription and try to return a simpler version.
     *
     * @param matchDescription String
     *
     * @return simple field match description if found, else original
     *     String.
     */
    static String parseMatchDescription(final String matchDescription) {
      Matcher matcher = pattern.matcher(matchDescription);
      if (matcher.find()) {
        return matcher.group(1).trim();
      }
      return matchDescription;
    }
  }
}
