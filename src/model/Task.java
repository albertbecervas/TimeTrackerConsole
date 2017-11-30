package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import observable.Clock;

/**
 * @invariant name != null
 * @invariant description != null
 * @version 2.00, 29/11/2017
 */

public class Task extends Item implements Serializable {

  private Project project;

  private static final long serialVersionUID = 1L;// Needed object identifier
  private ArrayList<Interval> intervals;

  /**
   * Constructor of Task
   * @param name Name of task.
   * @param description Description of task.
   * @param taskCallback Update callback function.
   */
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

  /**
   * Start the task.
   */
  public void start() {
    if (period.getDuration() == 0) {
      // The first time we start a task we set it's start working date and it'll never be updated.
      this.period.setStartWorkingDate(new Date()); 
    }
    this.isOpen = true;
    if (project != null) {
      project.start(); // We check if the task is in the main items list. 
    }
    setInterval();
  }

  /**
   * Stop the task.
   */
  public void stop() {
    this.isOpen = false;
    // Every time we stop an item inside the project, we update the finalWorkingDate.
    this.period.setFinalWorkingDate(new Date());
    if (project != null) {
      project.stop(); // We check if the task is in the main items list. 
    }
    Interval interval = this.intervals.get(intervals.size() - 1);// getting the last interval
    interval.setOpen(false);
  }

  /**
   * Update interval of task.
   * @param interval Interval to updates the task.
   */
  public void update(Interval interval) {
    try {
      period.addDuration(Clock.CLOCK_SECONDS);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    if (project != null) {
      project.update(this); // We check if the task is in the main items list. 
    }
  }

}
