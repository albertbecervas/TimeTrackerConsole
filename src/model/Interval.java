package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import observable.Clock;


public class Interval implements Serializable, Observer {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Task task;

    private Period period;
    private boolean isOpen;

    public Interval(Task task) {
        this.period = new Period(new Date());
        this.isOpen = true;
        this.task = task;
        Clock.getInstance().addObserver(this);
    }

    public void setEndWorkingLogDatee(Date endWorkingLogDate) {
        if (period == null) return;
        this.period.setFinalWorkingDate(endWorkingLogDate);
        this.period.setDuration(calculateDuration());
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }


    public Long calculateDuration(){
        return ((period.getFinalWorkingDate().getTime() - period.getStartWorkingDate().getTime()) / 1000);
    }

    public Long getDuration(){
        return period.getDuration();
    }

    public Date getFinalDate(){
        return period.getFinalWorkingDate();
    }

    public Date getInitialDate(){
        return period.getStartWorkingDate();
    }

    @Override
    public void update(Observable o, Object date) {

        if (isOpen) {
            this.setEndWorkingLogDatee((Date) date);
            if (period.getDuration() != 0) {
                task.update(this);
            }
        }

    }
}
