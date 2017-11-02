package model;

import utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class Task extends Item implements Serializable {

    private Project project;
    
    private static Logger logger = (Logger) LoggerFactory.getLogger(Interval.class);

    private static final long serialVersionUID = 1L;//Needed object identifier
    private ArrayList<Interval> intervals;

    private boolean isLimited;
    private boolean isProgrammed;
    private long maxDuration;

    public Task(String name, String description, Project taskCallback, boolean isLimited, boolean isProgrammed) {
    	logger.setLevel(Constants.LOGGER_LEVEL);
        this.name = name;
        logger.debug("Task name set as: " + this.name);
        this.description = description;
        logger.debug("Task description set as: " + this.description);
        this.period = new Period();
        this.isOpen = false;
        this.intervals = new ArrayList<>();

        this.isLimited = isLimited;
        this.isProgrammed = isProgrammed;
        this.maxDuration = 5L;

        this.project = taskCallback;
    }

    public void setInterval() {
        intervals.add(new Interval(this));
    }

    public ArrayList<Interval> getIntervals() {
        return intervals;
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


    public void start() {
        if (period.getDuration() == 0) this.period.setStartWorkingDate(new Date());//The first time we start a task we set it's start working date and it will never be updated
        this.isOpen = true;
        if (project != null) project.start();//We check if the task is in the main items list
        setInterval();
        logger.trace("Task " + this.getName() + " started.");
    }

    public void stop() {
        this.isOpen = false;
        this.period.setFinalWorkingDate(new Date());//Every time we stop an item inside the project, we update the finalWorkingDate
        if (project != null) project.stop();//We check if the task is in the main items list
        Interval interval = this.intervals.get(intervals.size() - 1);//getting the last interval
        interval.setOpen(false);
        logger.trace("Task " + this.getName() + " stopped.");
    }
    
    public void update(Interval interval){
        if (isLimited()) {
            if (period.getDuration() <= maxDuration) {
                period.addDuration(Constants.CLOCK_SECONDS);
            } else {
            	this.stop();
            }
        } else {
            period.addDuration(Constants.CLOCK_SECONDS);
        }

        if (project != null) project.update(this);//We check if the task is in the main items list

    }
}


