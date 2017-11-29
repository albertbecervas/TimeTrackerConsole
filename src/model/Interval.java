package model;

import observable.Clock;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;



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
    	if (period==null) throw new NullPointerException("period is null at method setEndWorkingLogDate");
        if (period == null) return;
        this.period.setFinalWorkingDate(endWorkingLogDate);
        try {
        	this.period.setDuration(calculateDuration());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }

    private Long calculateDuration(){
    	if (period==null) throw new NullPointerException("period is null at method calculateDuration");
    	Long duration = ((period.getFinalWorkingDate().getTime() - period.getStartWorkingDate().getTime()) / 1000); 
    	assert duration >= 0 : "duration is smaller than zero" + duration;
    	return duration;
    }

    public Long getDuration(){
    	if (period==null) throw new NullPointerException("period is null at method getDuration");
    	Long duration = period.getDuration();
    	assert duration >= 0 : "duration is smaller than zero" + duration;
        return duration;
    }

    public Date getFinalDate(){
    	if (period==null) throw new NullPointerException("period is null at method getFinalDate");
        return period.getFinalWorkingDate();
    }

    public Date getInitialDate(){
    	if (period==null) throw new NullPointerException("period is null at method getInitialDate");
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
