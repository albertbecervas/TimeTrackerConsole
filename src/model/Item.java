package model;

import java.io.Serializable;

/**
 * Item class contains the basic information of a time tracker item whether it is a task or a project.
 * <p>
 * Item implements Serializable in order to be saved as object in a file because we want to keep
 * the item tree state.
 * <p>
 * This class is public because will be called from other Classes
 */
public class Item implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
    protected String description;
    protected boolean isOpen;
    protected Period period;

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

    //generic item methods
    public String getFormattedTable() {
        if (period == null) return "";
        int secondsForHour = 3600;
        int secondsForMinut = 60;

        final long hours = period.getDuration() / secondsForHour;
        final long minuts = (period.getDuration() - hours * secondsForHour) / secondsForMinut;
        final long seconds = period.getDuration() - secondsForHour * hours - secondsForMinut * minuts;

        return String.valueOf(getName() + " ---> "
                + "duration = " + String.valueOf(hours + "h " + minuts + "m " + seconds + "s")) + " |"
                + "from: " + period.getStartWorkingDate() + " | "
                + "to: " + period.getFinalWorkingDate() + " |\n ";

    }
}

