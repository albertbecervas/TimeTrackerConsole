package reports;

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

public class BriefReport extends Report {

    private ArrayList<Item> items;

    private long duration = 0;

    public ArrayList<ItemReportDetail> projects;
    
    private Format format;
    
    public BriefReport(ArrayList<Item> items, String format) {
        this.items = items;
        projects = new ArrayList<ItemReportDetail>();
        
        if(format.equals("txt")){
        	this.format = new TextFormatPrinter();
        } else{
        	this.format = new HtmlFormatPrinter();
        }
    }

    public void generateBriefReport() {
        for (Item item : items) {
            long period = calculateItemDuration(item);
            
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
            projects.add(new ItemReportDetail(item.getName(), period/1000, initialDate, endDate));//add this to the node projects lists
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

        format.generateFile(this);
    }

    private long calculateItemDuration(Item item) {
        if (item instanceof Task) { //Basic case     	
        	        
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
            
        } else {
            for (Item subItem : ((Project) item).getItems()) {
                calculateItemDuration(subItem); //recursive function
            }
        }
        return duration;
    }
    
}
