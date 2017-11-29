package reports;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import elements.Element;

public class Report {

    protected String startDateString = "28/11/2017 21:24:30"; //dd/MM/yyyy hh:mm:ss
    protected String endDateString = "28/11/2017 21:24:40";
    protected long startPeriodTime;
    protected long endPeriodTime;

    protected String generationDate;
    
    protected ArrayList<Element> elements;

    public Report() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        try {
            //specified period which we want to get the report from
            startPeriodTime = df.parse(startDateString).getTime();
            endPeriodTime = df.parse(endDateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        elements = new ArrayList<Element>();
    }
    
    public ArrayList<Element> getElements(){
    	return elements;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public String getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(String generationDate) {
        this.generationDate = generationDate;
    }
}
