package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import observable.Clock;


public class Interval implements Serializable, Observer {

    private static final long serialVersionUID = 1L;//Needed object identifier

    private Task task;

    private Period period;
    private boolean isOpen;

    public Interval(Task task) {
        this.period = new Period();
        this.isOpen = true;
        this.task = task;
        Clock.getInstance().addObserver(this);
    }

    public void setEndWorkingLogDate(Date endWorkingLogDate) {
    	assert period != null : "Null period.";
        this.period.setFinalWorkingDate(endWorkingLogDate);
        this.period.setDuration(calculateDuration());
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }


    private Long calculateDuration(){
    	Long duration = ((period.getFinalWorkingDate().getTime() - period.getStartWorkingDate().getTime()) / 1000);
    	assert duration >= 0 : "Duration smaller than 0: " + duration; 
        return duration;
    }

    public Long getDuration(){
    	assert period != null : "Null period.";
        return period.getDuration();
    }

    public Date getFinalDate(){
    	assert period != null : "Null period.";
        return period.getFinalWorkingDate();
    }

    public Date getInitialDate(){
    	assert period != null : "Null period.";
    	assert period.getStartWorkingDate() instanceof Date : "Not an instance of Date.";
        return period.getStartWorkingDate();
    }

    @Override
    public void update(Observable o, Object date) {

        if (isOpen) {
            this.setEndWorkingLogDate((Date) date);
            if (period.getDuration() != 0) {
                task.update(this);
            }
        }

    }
}
