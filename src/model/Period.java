package model;


import java.io.Serializable;
import java.util.Date;

public class Period implements Serializable{

    private Date startWorkingDate;
    private Date finalWorkingDate;
    private long duration;

    public Period() {
        duration = 0L;
    }

    public Period(Date startWorkingDate){
        duration = 0L;
        this.startWorkingDate = startWorkingDate;
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
