package format;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import elements.Element;
import elements.Paragraph;
import elements.Separator;
import elements.Title;
import reports.Report;

public class HtmlFormatPrinter extends Format{

	public HtmlFormatPrinter(){	
	}

	@Override
	public void generateFile(Report report) {
        PrintWriter writer = null;
		try {
			writer = new PrintWriter("DetailedReport.html", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			
		for(Element element : report.getElements()){
			if(element instanceof Title){
				String titleHtml = "<h4>"
						+ element.getElement()
						+ "</h4>";
	            writer.print(titleHtml);
			}
			if(element instanceof Separator){
				String separatorHtml = "<h4>"
						+ element.getElement()
						+ "</h4>";
	            writer.println(separatorHtml);
			}
			if(element instanceof Paragraph){
				String paragraphHtml = "<p>"
						+ element.getElement()
						+ "</p>";
	            writer.println(paragraphHtml);
			}
		}
		
        writer.close();		
	}



	
}
