package reports;

import model.Interval;
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
 * Generates a detailed report of all items that shows: Duration, initial date, final date
 * and name of every item
 * @author Albert
 *
 */
public class DetailedReport extends Report {

    private ArrayList<ItemReportDetail> projects;
    private ArrayList<ItemReportDetail> subProjects;
    private ArrayList<ItemReportDetail> tasks;
    private ArrayList<ItemReportDetail> intervals;

    private long duration = 0;//global variable in order to keep value in recursive function
    private long projectDuration = 0;
    private long subProjectDuration = 0;
    private long taskDuration = 0;
    private long intervalDuration = 0;
    
    public DetailedReport(ArrayList<Item> items, String format) {
        this.items = items;
        
        initialiseLists();
        
        setFormat(format);
        
        generateDetailedReport();
    }

    private void initialiseLists(){
        projects = new ArrayList<>();
        subProjects = new ArrayList<>();
        tasks = new ArrayList<>();
        intervals = new ArrayList<>();
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
    
    public void generateDetailedReport() {

        for (Item item : items) {
        	if (item instanceof Project){
	            setProjectsDetails(item);
        	} else {
        		recursiveTreeSearch(item);
        	}
            duration = 0;
        }

        setReportElements();

        format.generateFile(this);
    }

    /**
     * Sets a list of projects with all calculated details
     * @param item Project that we want to calculate the details
     */
	private void setProjectsDetails(Item item) {
		projectDuration = recursiveTreeSearch(item);
		String initialDate = calculateInitialDate(item);
		String endDate = calculateFinalDate(item);
		
		projects.add(new ItemReportDetail(item.getName(), projectDuration/1000, initialDate, endDate));//add this to the node projects lists
	}

    /**
     * Iterates through the items in order to calculate every subItem parameters
     * @param item Project or task that we want to calculate the details
     * @return long The accumulate duration of all items inside
     */
    private long recursiveTreeSearch(Item item) {
        if (item instanceof Task) { 
        	//calculate the task duration and saves the details
        	//Also adds the task duration to the global duration variable in order
        	//to calculate the father project duration
            long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
            long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();

            calculateItemDuration(initialDateTime, endDateTime);
            String initialDate = calculateInitialDate(item);
            String endDate = calculateFinalDate(item);
            
            tasks.add(new ItemReportDetail(item.getName(), taskDuration/1000, initialDate, endDate));
            
            for (Interval interval : ((Task) item).getIntervals()) {
            	intervalDuration = calculateIntervalDuration(interval);
    	        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            	String initialIntervalDate = df.format(interval.getInitialDate());
            	String finalIntervalDate = df.format(interval.getFinalDate());

            	intervals.add(new ItemReportDetail(item.getName(), intervalDuration/1000 ,initialIntervalDate, finalIntervalDate));
        	}

        } else {
            for (Item subItem : ((Project) item).getItems()) {
                subProjectDuration = recursiveTreeSearch(subItem); //recursive function
                if (subItem instanceof Project) {
                    String initialDate = calculateInitialDate(item);
                    String endDate = calculateFinalDate(item);
                    
                    subProjects.add(new ItemReportDetail(subItem.getName(), subProjectDuration/1000, initialDate, endDate));
                }
                subProjectDuration = 0;
            }
        }
        return duration;
    }


    /**
     * This function calculates the included time of a Item in a concrete period
     * @param initialDateTime Initial date of the task in milliseconds
     * @param endDateTime Final date of the task in milliseconds
     */
	private void calculateItemDuration(long initialDateTime, long endDateTime) {
		if (initialDateTime > startPeriodTime && initialDateTime < endPeriodTime) {
			if (endDateTime < endPeriodTime) {
				taskDuration = endDateTime - initialDateTime;
				duration += taskDuration;
		    } else {
		    	taskDuration = endPeriodTime - initialDateTime;
		    	duration += taskDuration;
		    }
		} else if (endDateTime < endPeriodTime && endDateTime > startPeriodTime){
			if (initialDateTime> startPeriodTime) {
				taskDuration = endDateTime - startPeriodTime;
		    	duration += taskDuration;
		    } else {
		    	taskDuration = endDateTime - startPeriodTime;
		    	duration += taskDuration;
		    }
		} else if(initialDateTime < startPeriodTime && endDateTime > endPeriodTime){
			taskDuration = endPeriodTime - startPeriodTime;
			duration += taskDuration;
		}
	}

    /**
     * This function calculates the included time of a interval in a concrete period
     *
     * @param interval Interval that we want to calculate
     * @return duration of the interval included in the specified period
     */
    private long calculateIntervalDuration(Interval interval) {

        //complete period
        long startIntervalTime = interval.getInitialDate().getTime();
        long endIntervalTime = interval.getFinalDate().getTime();

        //if interval is finished after the start of the selected period
        if ((endIntervalTime > startPeriodTime) && (endIntervalTime < endPeriodTime)) {
            /**interval is started after the start of the selected period
             * 	TRUE: we return the complete interval time
             * 	FALSE: we return the included interval time
             */
            if (startIntervalTime > startPeriodTime) {
                return interval.getDuration();
            } else {
                return endIntervalTime - startPeriodTime;
            }
        }

        //if interval is started before the end of the selected period
        if ((startIntervalTime > startPeriodTime) && (startIntervalTime < endPeriodTime)) {
            /**interval is ended before the end of the selected period
             * 	TRUE: we return the complete interval time
             * 	FALSE: we return the included interval time
             */
            if (endIntervalTime < endPeriodTime) {
                return interval.getDuration();
            } else {
                return endPeriodTime - startIntervalTime;
            }
        }

        return 0;
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
		elements.add(new Title("INFORME DETALLAT"));
		elements.add(new Separator());
		elements.add(new Paragraph("desde-->  " + this.startDateString));
		elements.add(new Paragraph("fins-->  " + this.endDateString));
		elements.add(new Paragraph("generat-->  " + this.generationDate));
		elements.add(new Separator());
    	
		elements.add(new Title("Projects 	  Data_Inici  		 Data_final  	  Durada"));
        elements.add(new Separator());
        for(ItemReportDetail item : projects){
        	String paragraph = item.getName() 
        			+ "  |  "
        			+ item.getInitialDate()
        			+ "  |  "
        			+ item.getFinalDate()
        			+ "  |  "
        			+ item.getIncludedDuration();
        	elements.add(new Paragraph(paragraph));
        }
        elements.add(new Separator());
        
        elements.add(new Title("SubProjects 	  Data_Inici 		  Data_final  	  Durada"));
        elements.add(new Separator());
        for(ItemReportDetail item : subProjects){
        	String paragraph = item.getName() 
        			+ "  |  "
        			+ item.getInitialDate()
        			+ "  |  "
        			+ item.getFinalDate()
        			+ "  |  "
        			+ item.getIncludedDuration();
        	elements.add(new Paragraph(paragraph));
        }
        elements.add(new Separator());
        
        elements.add(new Title("Tasks  	 Data_Inici 		  Data_final 	   Durada"));
        elements.add(new Separator());
        for(ItemReportDetail item : tasks){
        	String paragraph = item.getName() 
        			+ "  |  "
        			+ item.getInitialDate()
        			+ "  |  "
        			+ item.getFinalDate()
        			+ "  |  "
        			+ item.getIncludedDuration();
        	elements.add(new Paragraph(paragraph));
        }
        elements.add(new Separator());
        
        elements.add(new Title("Intervals  	 Data_Inici 		  Data_final   	 Durada"));
        elements.add(new Separator());
        for(ItemReportDetail item : intervals){
        	String paragraph = item.getName() 
        			+ "  |  "
        			+ item.getInitialDate()
        			+ "  |  "
        			+ item.getFinalDate()
        			+ "  |  "
        			+ item.getIncludedDuration();
        	elements.add(new Paragraph(paragraph));
        }
        elements.add(new Separator());
	}

}
