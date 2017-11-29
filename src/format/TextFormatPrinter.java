package format;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import elements.Element;
import elements.Paragraph;
import elements.Separator;
import elements.Title;
import reports.Report;

public class TextFormatPrinter extends Format{
	
    public TextFormatPrinter(){
    }

	public void generateFile(Report report) {
		
        PrintWriter writer = null;
		try {
			writer = new PrintWriter("DetailedReport.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			
		for(Element element : report.getElements()){
			if(element instanceof Title){
	            writer.println(element.getElement());
			}
			if(element instanceof Separator){
	            writer.println(element.getElement());
			}
			if(element instanceof Paragraph){
	            writer.println(element.getElement());
			}
		}
		
        writer.close();

	}
}
