package reports;

import model.Interval;
import model.Item;
import model.Project;
import model.Task;

import java.util.ArrayList;

import elements.Paragraph;
import elements.Separator;
import elements.Title;
import format.Format;
import format.HtmlFormatPrinter;
import format.TextFormatPrinter;

public class DetailedReport extends Report {

    private ArrayList<Item> items;

    private ArrayList<ItemReportDetail> projects;
    private ArrayList<ItemReportDetail> subProjects;
    private ArrayList<ItemReportDetail> tasks;
    private ArrayList<ItemReportDetail> intervals;

    private long duration = 0;
    private long projectDuration = 0;
    private long subProjectDuration = 0;
    private long taskDuration = 0;
    private long intervalDuration = 0;
    
    private Format format;

    public DetailedReport(ArrayList<Item> items, String format) {
        this.items = items;
        projects = new ArrayList<>();
        subProjects = new ArrayList<>();
        tasks = new ArrayList<>();
        intervals = new ArrayList<>();
        
        if(format.equals("txt")){
        	this.format = new TextFormatPrinter();
        } else{
        	this.format = new HtmlFormatPrinter();
        }
        
    }

    public void generateDetailedReport() {

        for (Item item : items) {
            projectDuration = calculateItemDuration(item);

            long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
            long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();

            String initialDate;
            if (initialDateTime > startPeriodTime) {
                initialDate = item.getPeriod().getStartWorkingDate().toString();
            } else {
                initialDate = startDateString;
            }

            String endDate;
            if (endDateTime < endPeriodTime) {
                endDate = item.getPeriod().getFinalWorkingDate().toString();
            } else {
                endDate = endDateString;
            }
            projects.add(new ItemReportDetail(item.getName(), projectDuration/1000, initialDate, endDate));//add this to the node projects lists
            duration = 0;
        }

        elements.add(new Title("Projects   Data_Inici   Data_final    Durada"));
        elements.add(new Separator());
        for(ItemReportDetail item : projects){
        	String paragraph = item.getName() 
        			+ " --- "
        			+ item.getInitialDate()
        			+ " --- "
        			+ item.getFinalDate()
        			+ " --- "
        			+ item.getIncludedDuration();
        	elements.add(new Paragraph(paragraph));
        }
        
        elements.add(new Title("SubProjects   Data_Inici   Data_final    Durada"));
        elements.add(new Separator());
        for(ItemReportDetail item : subProjects){
        	String paragraph = item.getName() 
        			+ " --- "
        			+ item.getInitialDate()
        			+ " --- "
        			+ item.getFinalDate()
        			+ " --- "
        			+ item.getIncludedDuration();
        	elements.add(new Paragraph(paragraph));
        }
        
        elements.add(new Title("Tasks   Data_Inici   Data_final    Durada"));
        elements.add(new Separator());
        for(ItemReportDetail item : tasks){
        	String paragraph = item.getName() 
        			+ " --- "
        			+ item.getInitialDate()
        			+ " --- "
        			+ item.getFinalDate()
        			+ " --- "
        			+ item.getIncludedDuration();
        	elements.add(new Paragraph(paragraph));
        }
        
        elements.add(new Title("Intervals   Data_Inici   Data_final    Durada"));
        elements.add(new Separator());
        for(ItemReportDetail item : intervals){
        	String paragraph = item.getName() 
        			+ " --- "
        			+ item.getInitialDate()
        			+ " --- "
        			+ item.getFinalDate()
        			+ " --- "
        			+ item.getIncludedDuration();
        	elements.add(new Paragraph(paragraph));
        }

        format.generateFile(this);
    }

    private long calculateItemDuration(Item item) {
        if (item instanceof Task) { //Basic case

            long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
            long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();

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

            String initialDate;
            if (initialDateTime > startPeriodTime) {
                initialDate = item.getPeriod().getStartWorkingDate().toString();
            } else {
                initialDate = startDateString;
            }

            String endDate;
            if (endDateTime < endPeriodTime) {
                endDate = item.getPeriod().getFinalWorkingDate().toString();
            } else {
                endDate = endDateString;
            }
            ItemReportDetail task = new ItemReportDetail(item.getName(), taskDuration/1000, initialDate, endDate);
            tasks.add(task);
            
            for (Interval interval : ((Task) item).getIntervals()) {
            	intervalDuration = calculateIncludedTime(interval);
            	intervals.add(new ItemReportDetail(item.getName(), intervalDuration/1000 ,interval.getInitialDate().toString(), interval.getFinalDate().toString()));
        	}

        } else {
            for (Item subItem : ((Project) item).getItems()) {
                subProjectDuration = calculateItemDuration(subItem); //recursive function
                if (subItem instanceof Project) {
                    long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
                    long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();

                    String initialDate;
                    if (initialDateTime > startPeriodTime) {
                        initialDate = item.getPeriod().getStartWorkingDate().toString();
                    } else {
                        initialDate = startDateString;
                    }

                    String endDate;
                    if (endDateTime < endPeriodTime) {
                        endDate = item.getPeriod().getFinalWorkingDate().toString();
                    } else {
                        endDate = endDateString;
                    }
                    subProjects.add(new ItemReportDetail(subItem.getName(), subProjectDuration/1000, initialDate, endDate));
                }
                subProjectDuration = 0;
            }
        }
        return duration;
    }

    /**
     * This function calculated the included time of a interval in a concrete period
     *
     * @param interval
     * @return duration of the interval included in the specified period
     */
    private long calculateIncludedTime(Interval interval) {

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
}
