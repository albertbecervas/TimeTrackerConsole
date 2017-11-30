package format;

import elements.Paragraph;
import elements.Separator;
import elements.Title;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/*
 * When the superclass is called from an element this class prints it onto the file.
 * It also creates the file with the name given in the constructor.
 */
public class TextFormatPrinter extends Format {
  PrintWriter writer = null;

  public TextFormatPrinter(String fileTitle) {
    try {
      writer = new PrintWriter(fileTitle, "UTF-8");
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void print(Separator separator) {
    writer.println(separator.getElement());
  }

  @Override
  public void print(Title title) {
    writer.println(title.getElement());
  }

  @Override
  public void print(Paragraph paragraph) {
    writer.println(paragraph.getElement());
  }
  
  @Override
  public void close() {
    writer.close();
  }
}
