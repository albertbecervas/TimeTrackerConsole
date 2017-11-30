package format;

import elements.Paragraph;
import elements.Separator;
import elements.Title;

/*
 * Abstract superclass that defines the methods used to print the different types of element.
 * This class will be called when an element adds itself to the file.
 */

public abstract class Format {
  
  public abstract void print(Separator separator);
  
  public abstract void print(Title title);
  
  public abstract void print(Paragraph paragraph);
  
  public abstract void close();

}
