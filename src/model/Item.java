package model;

import java.io.Serializable;

/**
 * Item class contains the basic information of a time tracker item whether it is a task or a project.
 * <p>
 * Item implements Serializable in order to be saved as object in a file because we want to keep
 * the item tree state.
 * <p>
 * This class is public because will be called from other Classes
 * 
 * @invariant name != null;
 * @invariant description != null;
 *  
 */
public class Item implements Serializable {
    protected static final long serialVersionUID = 1L; //Needed version number in order to Serialise the object

    protected String name;
    protected String description;
    protected boolean isOpen;
    protected Period period;

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException{
    	if(name == null) throw new IllegalArgumentException("Name can not be null.");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
    	if(description == null) throw new IllegalArgumentException("Description can not be null.");
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

    public String getFormattedTable() {
        if (period == null) return "";
        int secondsPerHour = 3600;
        int secondsPerMinut = 60;

        final long hours = period.getDuration() / secondsPerHour;
        final long minutes = (period.getDuration() - hours * secondsPerHour) / secondsPerMinut;
        final long seconds = period.getDuration() - secondsPerHour * hours - secondsPerMinut * minutes;
				
        return String.valueOf(getName() + " ---> "
                + "duration = " + String.valueOf(hours + "h " + minutes + "m " + seconds + "s")) + " |"
                + "from: " + period.getStartWorkingDate() + " | "
                + "to: " + period.getFinalWorkingDate() + " |\n ";
        
    }
}

