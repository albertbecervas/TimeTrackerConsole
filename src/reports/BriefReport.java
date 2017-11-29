package reports;

import model.Item;
import model.Project;
import model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import elements.Paragraph;
import elements.Separator;
import elements.Title;
import format.HtmlFormatPrinter;
import format.TextFormatPrinter;

/**
 * Generates a Brief report of the main items that shows: Duration, initial date, final date
 * and name of every item
 * @author Albert
 *
 */
public class BriefReport extends Report {

    public ArrayList<ItemReportDetail> mainItems;

    private long duration = 0;
        
    public BriefReport(ArrayList<Item> items, String format) {
        this.items = items;
        
        mainItems = new ArrayList<ItemReportDetail>();
        
        setFormat(format);
        
        generateBriefReport();
    }
    
    /**
     * Sets the format of the file that will be generated
     */
    private void setFormat(String format){
    	switch(format){
    	case "txt":
        	this.format = new TextFormatPrinter();
        	break;
    	case "html":
    		this.format = new HtmlFormatPrinter();
    	}
    }
    

    public void generateBriefReport() {
        for (Item item : items) {
        	//calculates all item details and saves them in the projects lists
            setItemDetails(item);
        }
        
        setReportElements();

        format.generateFile(this);
    }
    
    /**
     * Sets a list of items with all calculated details
     * @param item Project or task that we want to calculate the details
     */
    private void setItemDetails(Item item) {
		long period = recursiveTreeSearch(item);
		String initialDate = calculateInitialDate(item);
		String endDate = calculateFinalDate(item);
		
		mainItems.add(new ItemReportDetail(item.getName(), period/1000, initialDate, endDate));//add this to the node projects lists
		duration = 0; //Reset the global variable in order to start again with the other item
	}

    /**
     * Iterates through the items in order to calculate every subItem parameters
     * @param item Project or task that we want to calculate the details
     * @return long The accumulate duration of all items inside
     */
    private long recursiveTreeSearch(Item item) {
        if (item instanceof Task) { //Basic case     	     
            calculateItemDuration(item);
        } else {
            for (Item subItem : ((Project) item).getItems()) {
                recursiveTreeSearch(subItem); //recursive function
            }
        }
        return duration;
    }

    /**
     * This function calculates the included time of a Item in a concrete period
     * @param item Item that we want to calculate
     */
	private void calculateItemDuration(Item item) {
		long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
		long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();

		if (initialDateTime > startPeriodTime && initialDateTime < endPeriodTime) {
			if (endDateTime < endPeriodTime) {
				duration += endDateTime - initialDateTime;
		    } else {
		    	duration += endPeriodTime - initialDateTime;
		    }
		} else if (endDateTime < endPeriodTime && endDateTime > startPeriodTime){
			if (initialDateTime> startPeriodTime) {
		    	duration += endDateTime - startPeriodTime;
		    } else {
		    	duration += endDateTime - startPeriodTime;
		    }
		} else if(initialDateTime < startPeriodTime && endDateTime > endPeriodTime){
			duration += endPeriodTime - startPeriodTime;
		}
	}
    
	private String calculateInitialDate(Item item) {
		long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
		String initialDate;
		if (initialDateTime > startPeriodTime) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		    initialDate = df.format(item.getPeriod().getStartWorkingDate());
		} else {
		    initialDate = startDateString;
		}
		return initialDate;
	}
	
    private String calculateFinalDate(Item item) {
		long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();
		String endDate;
		if (endDateTime < endPeriodTime) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		    endDate = df.format(item.getPeriod().getFinalWorkingDate());
		} else {
		    endDate = endDateString;
		}
		return endDate;
	}

    /**
     * Once all items are calculated, we set the elements that we want our report to have
     */
	private void setReportElements() {
		
		elements.add(new Separator());
		elements.add(new Title("INFORME BREU"));
		elements.add(new Separator());
		elements.add(new Paragraph("desde-->  " + this.startDateString));
		elements.add(new Paragraph("fins-->  " + this.endDateString));
		elements.add(new Paragraph("desde-->  " + this.generationDate));
		elements.add(new Separator());
		
		elements.add(new Title("Projects 	 Data_Inici 	 Data_final 	   Durada"));
        elements.add(new Separator());
        for(ItemReportDetail item : mainItems){
        	String paragraph = item.getName() 
        			+ "  |  "
        			+ item.getInitialDate()
        			+ "  |  "
        			+ item.getFinalDate()
        			+ "  |  "
        			+ item.getIncludedDuration();
        	elements.add(new Paragraph(paragraph));
        }
	}
}
