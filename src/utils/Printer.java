package utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.Interval;
import model.Item;
import model.Project;
import model.Task;
import observable.Clock;
import reports.ProjectReportDetail;
import reports.TaskReportDetail;

/**
 * Prints the items tree in a formatted table every period of time.
 */
public class Printer implements Observer {

	String startDateString = "27/11/2017 19:01:31"; //dd/MM/yyyy hh:mm:ss
	String endDateString = "27/11/2017 19:01:41";
	
    private ArrayList<Item> items;
    
    private ArrayList<TaskReportDetail> tasks;
    private ArrayList<ProjectReportDetail> projects;
    private ArrayList<ProjectReportDetail> subProjects;
    
	long duration = 0;
	long projectDuration = 0;
	long startPeriodTime;
	long endPeriodTime;
    
    public Printer(ArrayList<Item> items) {
        this.items = items;
        
        if(items == null){
        	items = ItemsTreeManager.getItems();
        }
        
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    	
    	tasks = new ArrayList();
    	projects = new ArrayList();
    	subProjects = new ArrayList();
    	//TODO remove when we don't want to debug
    	try {
			Date st = df.parse(startDateString);
	    	Date end = df.parse(endDateString);
	    	//specified period which we want to get the report from
			startPeriodTime = df.parse(startDateString).getTime();
			endPeriodTime = df.parse(endDateString).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//print("period-->"+st + " to " + end + "\n");
        
    }

    /**
     * Prints the items table in console in a formatted way
     */
    public void printTable(){
        Clock.getInstance().addObserver(this); //We need the clock in order to repeat the print of the table periodically
        print("\r \n\n TIME TABLE:\n");
        for (Item item : items) {
            recursivePrint(item, "");
        }
    }
    
    /**
     * Goes through all tree in order to print every item inside it
     * @param item
     * @param tabs
     */
    public void recursivePrint(Item item, String tabs) {
        if (item instanceof Task) {
            print(tabs + item.getFormattedTable());
        } else {
            print(tabs + "-" + item.getFormattedTable());
            tabs = tabs + "   "; //leaves some space in order to see in which item level are we
            for (Item subItem : ((Project) item).getItems()) {
                recursivePrint(subItem, tabs);
            }
        }
    }
    
    public void generateBriefReport(){
    	long duration = 0;

        //for every node project
		for (Item item : items) {
        	duration = calculateItemDuration(item);
			
			long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
			long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();
			
			String initialDate;
			if(initialDateTime > startPeriodTime ){
				initialDate = item.getPeriod().getStartWorkingDate().toString();
			} else {
				initialDate = startDateString;
			}
			
			String endDate;
			if(endDateTime < endPeriodTime){
				endDate = item.getPeriod().getFinalWorkingDate().toString();
			} else {
				endDate = endDateString;
			}
			projects.add(new ProjectReportDetail((Project)item, duration,initialDate,endDate));//add this to the node projects lists
        } 
		
		printTableDetailed();
    }
    
    public void printTableDetailed(){
    	PrintWriter writer;

		String header = String.format("%s %20s %20s %20s \r\n", "Projecte", "Data inici", "Data final", " Temps total");
		String separator = "__________________________________________________________\n";
    	ArrayList<String> table = new ArrayList<String>();
    	
    	table.add(header);
    	table.add(separator);
		
        for (ProjectReportDetail project : projects){
        	String projectReport = project.getProject().getName();
        	projectReport += " - ";
        	projectReport += project.getInitialDate();
        	projectReport += " - ";
        	projectReport += project.getFinalDate();
        	projectReport += " - ";
        	projectReport += project.getIncludedDuration();
        	projectReport += "\n";

        	table.add(projectReport);
        }
        
        table.add("\n"+header);
        table.add(separator);
        
        for (ProjectReportDetail project : subProjects){
        	String projectReport = project.getProject().getName();
        	projectReport += " - ";
        	projectReport += project.getInitialDate();
        	projectReport += " - ";
        	projectReport += project.getFinalDate();
        	projectReport += " - ";
        	projectReport += project.getIncludedDuration();
        	projectReport += "\n";

        	table.add(projectReport);
        }
        
        table.add("\n"+header);
        table.add(separator);
        
        for (TaskReportDetail task : tasks){
        	String projectReport = task.getTask().getName();
        	projectReport += " - ";
        	projectReport += task.getInitialDate();
        	projectReport += " - ";
        	projectReport += task.getFinalDate();
        	projectReport += " - ";
        	projectReport += task.getIncludedDuration();
        	projectReport += "\n";

        	table.add(projectReport);
        }
        
		try {
			writer = new PrintWriter("DetailedReport.txt", "UTF-8");
	        for (String row : table){
	        	writer.print(row);
	        	print(row);
	        }
	        
	        writer.close();
	   	} catch (FileNotFoundException | UnsupportedEncodingException e) {
	   		e.printStackTrace();
	   	}
	        
    }
    
    public void generateDetailedReport(){

    }
    
    public long calculateItemDuration(Item item){
    	if (item instanceof Task){ //Basic case
    		for (Interval interval : ((Task) item).getIntervals()){
				duration += calculateIncludedTime(interval);	
    		}
    		
			long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
			long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();
    		
			String initialDate;
			if(initialDateTime > startPeriodTime ){
				initialDate = item.getPeriod().getStartWorkingDate().toString();
			} else {
				initialDate = startDateString;
			}
			
			String endDate;
			if(endDateTime < endPeriodTime){
				endDate = item.getPeriod().getFinalWorkingDate().toString();
			} else {
				endDate = endDateString;
			}
    		TaskReportDetail task = new TaskReportDetail((Task) item,duration, initialDate, endDate);
    		tasks.add(task);
    		
    	} else {
    		for (Item subItem : ((Project) item).getItems()) {
                projectDuration += calculateItemDuration(subItem); //recursive function
                if(subItem instanceof Project){
	    			long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
	    			long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();
	        		
	    			String initialDate;
	    			if(initialDateTime > startPeriodTime ){
	    				initialDate = item.getPeriod().getStartWorkingDate().toString();
	    			} else {
	    				initialDate = startDateString;
	    			}
	    			
	    			String endDate;
	    			if(endDateTime < endPeriodTime){
	    				endDate = item.getPeriod().getFinalWorkingDate().toString();
	    			} else {
	    				endDate = endDateString;
	    			}
	        		subProjects.add(new ProjectReportDetail((Project) subItem, projectDuration, initialDate, endDate));
                }
            }
    	}
    	return duration;
    }
    
    /**
     * This function calculated the included time of a interval in a concrete period
     * @param interval
     * @return duration of the interval included in the specified period
     * @throws ParseException
     */
    private long calculateIncludedTime(Interval interval){
    	//print("------------------------------------------------------------\n");

    	//TODO remove when we don't want to debug
		//print("interval-->"+interval.getInitialDate() + " to " + interval.getFinalDate() + "\n");

    	//complete period
		long startIntervalTime = interval.getInitialDate().getTime();
		long endIntervalTime = interval.getFinalDate().getTime();

		//if interval is finished after the start of the selected period
		if((endIntervalTime > startPeriodTime) && (endIntervalTime < endPeriodTime)){
			/**interval is started after the start of the selected period
			 * 	TRUE: we return the complete interval time
			 * 	FALSE: we return the included interval time
			 */	
			if (startIntervalTime > startPeriodTime){
				return interval.getDuration();
			} else {
				return endIntervalTime - startPeriodTime;
			}
		}
		
		//if interval is started before the end of the selected period
		if((startIntervalTime > startPeriodTime) && (startIntervalTime < endPeriodTime )){
			/**interval is ended before the end of the selected period
			 * 	TRUE: we return the complete interval time
			 * 	FALSE: we return the included interval time
			 */	
			if(endIntervalTime < endPeriodTime){
				return interval.getDuration();
			} else {
				return endPeriodTime - startIntervalTime;
			}
		}
		
		return 0;
    }

    public void print(String text) {
        System.out.print(text);
    }

    @Override
    public void update(Observable o, Object arg) {
        printTable();
    }

}
