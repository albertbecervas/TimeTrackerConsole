package elements;

import format.Format;

/*
 * Element subclass that implements the printing of a separator onto a format instance,
 * which is given as a parameter.
 */

public class Separator extends Element {

  String separator = "----------------------------------------------";

  @Override
  public String getElement() {
    return separator;
  }

  @Override
  public void print(Format format) {
    format.print(this);
  }

}
