package model;

import java.io.Serializable;

/**
 * Item class contains the basic information of a time tracker item whether it
 * is a task or a project. Item implements Serializable in order to be saved as
 * object in a file because we want to keep the item tree state. This class is
 * public because will be called from other Classes
 * 
 * @invariant name != null
 * @invariant description != null
 * @version 2.00, 29/11/2017
 */
public class Item implements Serializable {
  protected static final long serialVersionUID = 1L; // Version in order to Serialize the object.

  protected String name;
  protected String description;
  protected boolean isOpen;
  protected Period period;

  // getters and setters
  public String getName() {
    return name;
  }

  /**
   * Set name of project or task.
   * @param name Name of project or task.
   * @throws IllegalArgumentException Invalid name.
   */
  public void setName(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name can not be null."); 
    }
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  /**
   * Set description of project or task.
   * @param description Description of project or task.
   */
  public void setDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("Description can not be null."); 
    }
    this.description = description;
  }

  public boolean isOpen() {
    return isOpen;
  }

  public void setOpen(boolean isOpen) {
    this.isOpen = isOpen;
  }

  public Period getPeriod() {
    return period;
  }

  public void setPeriod(Period period) {
    this.period = period;
  }

  /**
   * Get a formatted table.
   */
  public String getFormattedTable() {
    if (period == null) {
      return ""; 
    }
    int secondsPerHour = 3600;
    int secondsPerMinut = 60;

    final long hours = period.getDuration() / secondsPerHour;
    final long minutes = (period.getDuration() - hours * secondsPerHour) / secondsPerMinut;
    final long seconds = period.getDuration() - secondsPerHour * hours - secondsPerMinut * minutes;

    return String.valueOf(getName() + " ---> " + "duration = " + String.valueOf(hours + "h " 
        + minutes + "m " + seconds + "s")) + " |" + "from: " + period.getStartWorkingDate() + " | "
        + "to: " + period.getFinalWorkingDate() + " |\n ";

  }
}
