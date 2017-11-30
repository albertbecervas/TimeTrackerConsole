package format;

import elements.Paragraph;
import elements.Separator;
import elements.Title;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/*
 * When the superclass is called from an element this class prints it onto the file,
 * adding the corresponding html tags. It also creates the file with the name given 
 * in the constructor.
 */
public class HtmlFormatPrinter extends Format {
  
  PrintWriter writer = null;

  public HtmlFormatPrinter(String fileTitle) {    
    try {
      writer = new PrintWriter(fileTitle, "UTF-8");
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void print(Separator separator) {
    String separatorHtml = "<h4>" + separator.getElement() + "</h4>";
    writer.println(separatorHtml);    
  }

  @Override
  public void print(Title title) {
    String titleHtml = "<h4>" + title.getElement() + "</h4>";
    writer.print(titleHtml);    
  }

  @Override
  public void print(Paragraph paragraph) {
    String paragraphHtml = "<p>" + paragraph.getElement() + "</p>";
    writer.println(paragraphHtml);    
  }
  
  @Override
  public void close() {
    writer.close();
  }

}
