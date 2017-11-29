package reports;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import elements.Element;
import format.Format;
import model.Item;

/**
 * Generic class of a report that keeps the common data in all report types
 * @invariant startDateString != null;
 * @invariant endDateString != null;
 */
public class Report {

    protected String startDateString = "28/11/2017 21:24:30"; //dd/MM/yyyy hh:mm:ss
    protected String endDateString = "28/11/2017 21:24:40";
    protected long startPeriodTime;
    protected long endPeriodTime;

    protected String generationDate;
    
    protected ArrayList<Item> items;

    protected ArrayList<Element> elements;
    
    protected Format format;

    public Report() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        assert startDateString != null : "startDateString is null";
        assert endDateString != null : "endDateString is null";
        try {
            //specified period which we want to get the report from
            startPeriodTime = df.parse(startDateString).getTime();
            endPeriodTime = df.parse(endDateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        generationDate = df.format(new Date());
        elements = new ArrayList<Element>();
    }
    
    public ArrayList<Element> getElements(){
    	return elements;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) throws NullPointerException{
    	if(startDateString == null) throw new NullPointerException("startDateString is null");
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) throws NullPointerException{
    	if(endDateString == null) throw new NullPointerException("endDateString is null");
        this.endDateString = endDateString;
    }

    public String getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(String generationDate) throws NullPointerException{
    	if(generationDate == null) throw new NullPointerException("generationDate is null");
        this.generationDate = generationDate;
    }
}
