package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import callback.IntervalCallback;
import observable.Clock;
import utils.Constants;


public class Interval implements Serializable, Observer {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private static Logger logger = (Logger) LoggerFactory.getLogger(Interval.class);
	
    private IntervalCallback mCallback;

    private Period period;
    private boolean isOpen;

    public Interval(Task task) {
    	logger.setLevel(Constants.LOGGER_LEVEL);
        this.period = new Period(new Date());
        this.isOpen = true;
        this.mCallback = task;
        Clock.getInstance().addObserver(this);
        logger.debug("task " + task.name + "'s interval started");
    }

    public void setEndWorkingLogDatee(Date endWorkingLogDate) {
        if (period == null) {
        	logger.warn("Trying to set an end working date to a null period.");
        	return;
        }
        this.period.setFinalWorkingDate(endWorkingLogDate);
        this.period.setDuration(calculateDuration());
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
        logger.debug("Interval set as open?: " + this.isOpen);
    }

    public boolean isOpen() {
        return isOpen;
    }


    public Long calculateDuration(){
        Long duration = ((period.getFinalWorkingDate().getTime() - period.getStartWorkingDate().getTime()) / 1000);
        logger.debug("Interval's duration calculated: " + duration);
        return duration;
    }

    public Long getDuration(){
        return period.getDuration();
    }

    public Date getFinalDate(){
        return period.getFinalWorkingDate();
    }

    public Date getInitialDate(){
        return period.getStartWorkingDate();
    }

    @Override
    public void update(Observable o, Object date) {

        if (isOpen) {
            this.setEndWorkingLogDatee((Date) date);
            if (period.getDuration() != 0) {
                mCallback.update(this);
                logger.trace("Task updated.");
            }
        }

    }
}
