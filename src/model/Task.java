package model;

import observable.Clock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @invariant name != null;
 * @invariant description != null;
 */

public class Task extends Item implements Serializable {

    private Project project;

    private static final long serialVersionUID = 1L;//Needed object identifier
    private ArrayList<Interval> intervals;

    public Task(String name, String description, Project taskCallback) {
        this.name = name;
        this.description = description;
        this.period = new Period();
        this.isOpen = false;
        this.intervals = new ArrayList<>();

        this.project = taskCallback;
    }

    public void setInterval() {
        intervals.add(new Interval(this));
    }

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public void start() {
        if (period.getDuration() == 0) this.period.setStartWorkingDate(new Date());//The first time we start a task we set it's start working date and it will never be updated
        this.isOpen = true;
        if (project != null) project.start();//We check if the task is in the main items list
        setInterval();
    }

    public void stop() {
        this.isOpen = false;
        this.period.setFinalWorkingDate(new Date());//Every time we stop an item inside the project, we update the finalWorkingDate
        if (project != null) project.stop();//We check if the task is in the main items list
        Interval interval = this.intervals.get(intervals.size() - 1);//getting the last interval
        interval.setOpen(false);
    }
    
    public void update(Interval interval){
        try {
    		period.addDuration(Clock.CLOCK_SECONDS);
    	}catch(IllegalArgumentException e) {
    		e.printStackTrace();
    	}
        if (project != null) project.update(this);//We check if the task is in the main items list
    }

}


