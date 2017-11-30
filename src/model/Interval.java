package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import observable.Clock;

/*
 * Interval class contains the information of an interval which belongs to a task.
 * Implements the observer in order to update the time and the serializable in order
 * to be saved correctly.
 */

public class Interval implements Serializable, Observer {

  private static final long serialVersionUID = 1L;// Needed object identifier

  private Task task;

  private Period period;
  private boolean isOpen;

  /**
   * Constructor of Interval.
   * @param task Item of Task type.
   */
  public Interval(Task task) {
    this.period = new Period();
    this.isOpen = true;
    this.task = task;
    Clock.getInstance().addObserver(this);
  }
  
  /**
   * Set end data of working.
   * @param endWorkingLogDate End data of working.
   */
  public void setEndWorkingLogDate(Date endWorkingLogDate) {
    if (period == null) {
      throw new NullPointerException("period is null at method setEndWorkingLogDate");      
    }
    if (period == null) {
      return;
    }
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

  private Long calculateDuration() {
    if (period == null) {
      throw new NullPointerException("period is null at method calculateDuration"); 
    }
    Long duration = ((period.getFinalWorkingDate().getTime() 
        - period.getStartWorkingDate().getTime()) / 1000);
    assert duration >= 0 : "duration is smaller than zero" + duration;
    return duration;
  }

  /**
   * Get duration of project or task.
   * @return duration Duration of project or task.
   */
  public Long getDuration() {
    if (period == null) {
      throw new NullPointerException("period is null at method getDuration"); 
    }
    Long duration = period.getDuration();
    assert duration >= 0 : "duration is smaller than zero" + duration;
    return duration;
  }

  /**
   * Get final date of project or task.
   * @return period.getFinalWorkingDate() End data working.
   */
  public Date getFinalDate() {
    if (period == null) {
      throw new NullPointerException("period is null at method getFinalDate"); 
    }
    return period.getFinalWorkingDate();
  }
  
  /**
   * Get initial date of project or task.
   * @return period.getStartWorkingDate() Start date of project or task.
   */
  public Date getInitialDate() {
    if (period == null) {
      throw new NullPointerException("period is null at method getInitialDate"); 
    }
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
