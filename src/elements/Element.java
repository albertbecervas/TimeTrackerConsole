package elements;

import format.Format;

/*
 * Abstract superclass that joins the different elements that a report can have.
 * It defines the functions getElement and print, which are implemented in its subclasses.
 * It is visited by the subclasses of Report. 
 */

public abstract class Element {

  public abstract String getElement();
  
  public abstract void print(Format format);

}
