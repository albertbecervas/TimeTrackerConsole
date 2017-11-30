package elements;

import format.Format;

/*
 * Element subclass that implements the printing of a paragraph onto a format instance,
 * which is given as a parameter.
 */

public class Paragraph extends Element {

  String paragraph;

  public Paragraph(String paragraph) {
    this.paragraph = paragraph;
  }

  @Override
  public String getElement() {
    return paragraph;
  }

  @Override
  public void print(Format format) {
    format.print(this);    
  }

}
