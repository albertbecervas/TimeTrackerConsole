package format;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import elements.Element;
import reports.BriefReport;
import reports.Report;

/**
 * When the father class is called from a report this class generates 
 * a report in text format
 * @author Albert
 *
 */
public class TextFormatPrinter extends Format{
	
    public TextFormatPrinter(){
    }

	public void generateFile(Report report) {
		
        PrintWriter writer = null;
        String fileTitle = " ";
        
        if (report instanceof BriefReport){
        	fileTitle = "BriefReport.txt";
        } else {
        	fileTitle = "DetailedReport.txt";
        }
        
		try {
			writer = new PrintWriter(fileTitle, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			
		for(Element element : report.getElements()){
			writer.println(element.getElement());
		}
		
        writer.close();

	}
}
