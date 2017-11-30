package elements;

import format.Format;

/*
 * Element subclass that implements the printing of a title onto a format instance,
 * which is given as a parameter.
 */

public class Title extends Element {

  private String title;

  public Title(String title) {
    this.title = title;
  }

  @Override
  public String getElement() {
    return title;
  }

  @Override
  public void print(Format format) {
    format.print(this);    
  }

}
