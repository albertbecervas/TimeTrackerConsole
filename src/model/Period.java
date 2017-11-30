package model;

import java.io.Serializable;
import java.util.Date;

/**
 * @invariant duration >=0
 * @invariant startWorkingDate != null
 * @invariant finalWorkingDate != null
 * @version 2.00, 29/11/2017
 */
public class Period implements Serializable {

  private static final long serialVersionUID = 1L;
  private Date startWorkingDate;
  private Date finalWorkingDate;
  private long duration;

  public Period() {
    duration = 0L;
    this.startWorkingDate = new Date();
  }

  public Date getStartWorkingDate() {
    return startWorkingDate;
  }

  public void setStartWorkingDate(Date startWorkingDate) {
    this.startWorkingDate = startWorkingDate;
  }

  public Date getFinalWorkingDate() {
    return finalWorkingDate;
  }

  public void setFinalWorkingDate(Date finalWorkingDate) {
    this.finalWorkingDate = finalWorkingDate;
  }

  public long getDuration() {
    return duration;
  }

  /**
   * Set a duration of project or task.
   * @param duration Duration of project or task.
   * @throws IllegalArgumentException Invalid argument.
   */
  public void setDuration(long duration) throws IllegalArgumentException {
    if (duration < 0) {
      throw new IllegalArgumentException("Duration must be positive or zero."); 
    }
    this.duration = duration;
  }

  /**
   * Increment duration of project or task.
   * @param duration Duration to increment.
   */
  public void addDuration(long duration) {
    if (duration < 0) {
      throw new IllegalArgumentException("Duration must be positive or zero.");
    }
    this.duration += duration;
  }
}
