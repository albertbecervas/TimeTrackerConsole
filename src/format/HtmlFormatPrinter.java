package format;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import elements.Element;
import elements.Paragraph;
import elements.Separator;
import elements.Title;
import reports.BriefReport;
import reports.Report;

/**
 * When the father class is called from a report this class generates 
 * a report in web format
 *
 */
public class HtmlFormatPrinter extends Format{

	public HtmlFormatPrinter(){	
	}

	@Override
	public void generateFile(Report report) {
        PrintWriter writer = null;
        String fileTitle = " ";
        
        if (report instanceof BriefReport){
        	fileTitle = "BriefReport.html";
        } else {
        	fileTitle = "DetailedReport.html";
        }
             
		try {
			writer = new PrintWriter(fileTitle, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			
		for(Element element : report.getElements()){
			if(element instanceof Title){
				String titleHtml = "<h4>"
						+ element.getElement()
						+ "</h4>";
	            writer.print(titleHtml);
			}else if(element instanceof Separator){
				String separatorHtml = "<h4>"
						+ element.getElement()
						+ "</h4>";
	            writer.println(separatorHtml);
			}else if(element instanceof Paragraph){
				String paragraphHtml = "<p>"
						+ element.getElement()
						+ "</p>";
	            writer.println(paragraphHtml);
			}else {
				assert false : "Element is not an instance of one of the defined classes.";
			}
		}
		
		assert writer != null : "Writer is null.";
        writer.close();		
	}



	
}
