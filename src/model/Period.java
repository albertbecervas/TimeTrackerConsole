package model;


import java.io.Serializable;
import java.util.Date;

/**
 * @invariant duration >=0
 * @author Albert
 *
 */
public class Period implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date startWorkingDate;
    private Date finalWorkingDate;
    private long duration;
    
    public Period(){
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

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void addDuration(long duration){
        this.duration += duration;
    }
}
