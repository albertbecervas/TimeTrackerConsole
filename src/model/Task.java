package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import callback.IntervalCallback;
import callback.TaskCallback;
import observable.Clock;

public class Task extends Item implements Serializable, IntervalCallback {
	
	TaskCallback mCallback;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date initialWorkingDate;
    private Date finalWorkingDate;
    private ArrayList<Interval> intervals;

    private boolean isLimited;
    private boolean isProgrammed;
    private long maxDuration;

    public Task() {
        initialWorkingDate = new Date();
        finalWorkingDate = new Date();
        intervals = new ArrayList<>();
        isOpen = false;
        this.duration = 0L;
        this.itemType = "1";
    }

    public Task(String name, String description, TaskCallback taskCallback, boolean isLimited, boolean isProgrammed) {
        this.name = name;
        this.description = description;
        initialWorkingDate = new Date();
        finalWorkingDate = new Date();
        intervals = new ArrayList<>();
        isOpen = false;
        this.duration = 0L;
        this.itemType = "1";
        
        this.mCallback = taskCallback;

        this.isLimited = isLimited;
        this.isProgrammed = isProgrammed;
        this.maxDuration = 120L;
    }

    public Date getInitialWorkingDate() {
        return initialWorkingDate;
    }

    public Date getFinalWorkingDate() {
        return finalWorkingDate;
    }

    public void setFinalWorkingDate(Date finalWorkingDate) {
        this.finalWorkingDate = finalWorkingDate;
    }

    public void setInterval(Date startDate, Date endDate) {
        intervals.add(new Interval(startDate, endDate, this));
    }

    public void updateInterval(Date endDate) {

        Interval interval = intervals.get(intervals.size() - 1);
        interval.setEndWorkingLogDatee(endDate);
    }

    public void closeInterval(Date endDate) {
        Interval interval = intervals.get(intervals.size() - 1);
        interval.setEndWorkingLogDatee(endDate);
        setDuration(interval.getDuration());
    }

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }
    
    
    public void start(){
    	this.isOpen = true;
    	setInterval(new Date(),new Date());
    }
    
    public void stop(){
    	this.isOpen = false;
    	Interval interval = this.intervals.get(intervals.size()-1);
    	interval.setOpen(false);
    }




    public boolean isLimited() {
        return isLimited;
    }

    public void setLimited(boolean limited) {
        isLimited = limited;
    }

    public boolean isProgrammed() {
        return isProgrammed;
    }

    public void setProgrammed(boolean programmed) {
        isProgrammed = programmed;
    }

    public long getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(long maxDuration) {
        this.maxDuration = maxDuration;
    }

	@Override
	public void update(Interval interval) {
		
		if (isOpen){
	        if (interval.getDuration() != 0){
	        	addDuration(Clock.CLOCK_SECONDS);

		        mCallback.update(this);
	        }
		}
	}
}

