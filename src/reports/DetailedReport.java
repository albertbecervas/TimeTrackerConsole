package reports;

import model.Interval;
import model.Item;
import model.Project;
import model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import elements.Paragraph;
import elements.Separator;
import elements.Title;
import format.HtmlFormatPrinter;
import format.TextFormatPrinter;

/**
 * Generates a detailed report of all items that shows: Duration, initial date, final date
 * and name of every item
 *
 */
public class DetailedReport extends Report {

    private ArrayList<ItemReportDetail> projects;
    private ArrayList<ItemReportDetail> subProjects;
    private ArrayList<ItemReportDetail> tasks;
    private ArrayList<ItemReportDetail> intervals;

    private long duration = 0;//global variable in order to keep value in recursive function
	long subProjectDuration= 0;

    private DateFormat df;

    
    public DetailedReport(ArrayList<Item> items, String format) {
        this.items = items;
        
        this.df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        
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
        		recursiveTreeSearch(item, false);
        	}
            duration = 0;
        }

        setReportElements();

        format.generateFile(this);
    }

    /**
     * Iterates through the items in order to calculate every subItem parameters
     * @param item Project or task that we want to calculate the details
     * @return long The accumulate duration of all items inside
     */
    private long recursiveTreeSearch(Item item,boolean isSubProject) {
    	long itemDuration=0;
        if (item instanceof Task) { 

            for (Interval interval : ((Task) item).getIntervals()) {
            	
            	itemDuration = setIntervalDetails(item, itemDuration, interval);
    		}

            setTaskDetails(item, isSubProject, itemDuration);

        } else {
            for (Item subItem : ((Project) item).getItems()) {
                recursiveTreeSearch(subItem, true); //recursive function
                System.out.println("duration-->"+ subItem.getName()+"-->"+ subProjectDuration);
                if (subItem instanceof Project) {
                    setSubProjectDetails(subItem);
                }
                if(!isSubProject){
                	subProjectDuration = 0;
                }
            }
        }
        return itemDuration;
    }
    

    /**
     * Sets a list of projects with all calculated details
     * @param item Project that we want to calculate the details
     */
	private void setProjectsDetails(Item item) {
		recursiveTreeSearch(item, false );
		Date initialDate = calculateInitialDate(item);
		Date endDate = calculateFinalDate(item);
		
		String initialDateText = df.format(initialDate);
        String endDateText = df.format(endDate);
		
		projects.add(new ItemReportDetail(item.getName(), duration/1000, initialDateText, endDateText));//add this to the node projects lists
	}
	
    /**
     * Sets a list of sub projects with all calculated details
     * @param subItem Project that we want to calculate the details
     */
	private void setSubProjectDetails(Item subItem) {
		Date initialDate = calculateInitialDate(subItem);
		Date endDate = calculateFinalDate(subItem);
		
		String initialDateText;
		String endDateText;
		if ( initialDate != null && endDate != null){
		    initialDateText = df.format(initialDate);
		    endDateText = df.format(endDate);
		} else {
			initialDateText = "-";
			endDateText = "-";
		}
		
		subProjects.add(new ItemReportDetail(subItem.getName(), subProjectDuration/1000, initialDateText, endDateText));
	}

	/**
     * Sets a list of tasks with all calculated details
	 * @param item task that we want to calculate the details
	 * @param isSubProject boolean to check if the father is a sub project
	 * @param itemDuration duration of the task to return in the iterative function
	 */
	private void setTaskDetails(Item item, boolean isSubProject, long itemDuration) {
		Date initialDate = calculateInitialDate(item);
		Date endDate = calculateFinalDate(item);
		duration += itemDuration;//update the projects time
		if(isSubProject){
			subProjectDuration += itemDuration;
		}
		
		String initialDateText;
		String endDateText;
		if(initialDate != null && endDate != null){
		    initialDateText = df.format(initialDate);
		    endDateText = df.format(endDate);
		} else {
			initialDateText = "-";
			endDateText = "-";
		}
		
		tasks.add(new ItemReportDetail(item.getName(), itemDuration/1000, initialDateText, endDateText));
	}

	/**
     * Sets a list of tasks with all calculated details
	 * @param item task that we want to calculate the details of the interval
	 * @param itemDuration duration of the task to return in the iterative function
	 * @param interval that we want to set details
	 * @return
	 */
	private long setIntervalDetails(Item item, long itemDuration, Interval interval) {
		long intervalDuration = 0;
		Date initialDate = calculateInitialDate(interval);
		Date endDate = calculateFinalDate(interval);
		
		String initialDateText;
		String endDateText;
		if(initialDate != null && endDate != null){
		    intervalDuration = endDate.getTime() - initialDate.getTime();
		    itemDuration += intervalDuration;
		    initialDateText = df.format(initialDate);
		    endDateText = df.format(endDate);
		} else {
			initialDateText = "-";
			endDateText = "-";
			intervalDuration = 0;
		}

		intervals.add(new ItemReportDetail(item.getName(), intervalDuration/1000 ,initialDateText, endDateText));
		return itemDuration;
	}
    
    private Date calculateInitialDate(Item item){
    	long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
    	long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();
    	
    	if(initialDateTime <= startPeriodTime){
    		if(endDateTime <= startPeriodTime){
    			return null;
    		}
    		return startPeriodDate;
    	} else if( initialDateTime > startPeriodTime && initialDateTime <= endPeriodTime){
    		return item.getPeriod().getStartWorkingDate();
    	} else {
    		return null;
    	}    	
    }
    
    private Date calculateInitialDate(Interval interval){
    	long initialDateTime = interval.getInitialDate().getTime();
    	long endDateTime = interval.getFinalDate().getTime();
    	
    	if(initialDateTime <= startPeriodTime){
    		if(endDateTime <= startPeriodTime){
    			return null;
    		}
    		return startPeriodDate;
    	} else if( initialDateTime > startPeriodTime && initialDateTime <= endPeriodTime){
    		return interval.getInitialDate();
    	} else {
    		return null;
    	}    	
    }
    
    private Date calculateFinalDate(Item item){
    	long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
    	long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();
    	
    	if(endDateTime >= endPeriodTime){
    		if(initialDateTime >= endPeriodTime){
    			return null;
    		}
    		return endPeriodDate;
    	} else if( endDateTime < endPeriodTime && endDateTime >= startPeriodTime){
    		return item.getPeriod().getFinalWorkingDate();
    	} else {
    		return null;
    	} 
    }
    
    private Date calculateFinalDate(Interval interval){
    	long initialDateTime = interval.getInitialDate().getTime();
    	long endDateTime = interval.getFinalDate().getTime();
    	
    	if(endDateTime >= endPeriodTime){
    		if(initialDateTime >= endPeriodTime){
    			return null;
    		}
    		return endPeriodDate;
    	} else if( endDateTime < endPeriodTime && endDateTime >= startPeriodTime){
    		return interval.getFinalDate();
    	} else {
    		return null;
    	} 
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
