package org.fyrz.solr.explain.parser.sequences;

import org.fyrz.solr.explain.parser.types.ExplainElement;
import org.fyrz.solr.explain.parser.types.ExplainElementType;

import java.util.List;

public abstract class ExplainElementSequence<T> {

  public abstract boolean isDescribedBy(final List<ExplainElement> explainElements);

  public abstract T interprets(final List<ExplainElement> explainElements, T object);

  /**
   * Method to check if a ordered list matches a passed prefix sequence and
   * does not contain any prohibited elements.
   *
   * @param explainElements sequence to check.
   * @param sequence prefix sequence.
   * @param prohibitedElements elements prohibited in sequence.
   * @return boolean value indicating if explainElements is valid for this type
   *     of ExplainElementSequence.
   */
  static boolean checkSequence(final List<ExplainElement> explainElements,
      ExplainElementType[] sequence, ExplainElementType[] prohibitedElements) {
    boolean ret = false;
    if (explainElements.size() >= sequence.length) {
      ret = true;
      for (int i = 0; i < explainElements.size(); i++) {
        if (i < sequence.length) {
          // test if prefix sequence matches
          if (!explainElements.get(i).getType().equals(sequence[i])) {
            ret = false;
            break;
          }
        } else {
          // test for prohibited elements in the remaining sequence
          for (ExplainElementType explainElementType : prohibitedElements) {
            if (explainElementType.equals(explainElements.get(i).getType())) {
              ret = false;
              break;
            }
          }
        }
      }
    }
    return ret;
  }
}
