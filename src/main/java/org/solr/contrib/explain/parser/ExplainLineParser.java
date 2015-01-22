package org.solr.contrib.explain.parser;

import org.solr.contrib.explain.parser.types.ExplainElement;
import org.solr.contrib.explain.parser.types.ExplainElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExplainLineParser {

  private Pattern preCompiledPattern;

  public ExplainLineParser() {
    preCompilePatterns();
  }

  private void preCompilePatterns() {
    final StringBuilder explainPatternsBuffer = new StringBuilder();
    for (final ExplainElementType explainElementType : ExplainElementType.values()) {
      explainPatternsBuffer.append(String.format("|(?<%s>%s)", explainElementType.name(), explainElementType.getPattern()));
    }
    preCompiledPattern = Pattern.compile(explainPatternsBuffer.substring(1));
  }

  public List<ExplainElement> parseSingleLine(final String line) {
    List<ExplainElement> explainElements = new ArrayList<>();

    // Match elements within the line
    Matcher matcher = preCompiledPattern.matcher(line);
    outerLoop:
    while (matcher.find()) {
      for (ExplainElementType explainElementType : ExplainElementType.values()) {
        if (matcher.group(explainElementType.name()) != null &&
            explainElementType != ExplainElementType.WHITESPACE) {
          explainElements.add(new ExplainElement(explainElementType, matcher.group(explainElementType.name())));
          continue outerLoop;
        }
      }
    }
    return explainElements;
  }

}
