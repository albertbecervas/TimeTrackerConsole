package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import callback.IntervalCallback;
import observable.Clock;

public class Interval implements Serializable, Observer {
	
	private IntervalCallback mCallback;


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date startWorkingLogDate;
    private Date endWorkingLogDate;
    private long duration;
    private boolean isOpen;

    public Interval() {
        //empty constructor
    	Clock.getInstance().addObserver(this);
    }

    public Interval(Date startWorkingLogDate, Date endWorkingLogDate, Task task) {
        this.startWorkingLogDate = startWorkingLogDate;
        this.endWorkingLogDate = endWorkingLogDate;
        this.isOpen = true;
        this.mCallback = task;
        Clock.getInstance().addObserver(this);
    }

    public Date getStartWorkingLogDate() {
        return startWorkingLogDate;
    }

    public Date getEndWorkingLogDate() {
        return endWorkingLogDate;
    }

    public void setEndWorkingLogDate(Date endWorkingLogDate) {
        this.endWorkingLogDate = endWorkingLogDate;
    }

    public void setEndWorkingLogDatee(Date endWorkingLogDate) {
        this.endWorkingLogDate = endWorkingLogDate;
        duration = ((getEndWorkingLogDate().getTime() - getStartWorkingLogDate().getTime()) / 1000);
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public long getDuration() {
        return duration;
    }

    //TODO in english
    public String getFormattedDuration() {
        int segonsPerHora = 3600;
        int segonsPerMinut = 60;

        long durada = getDuration();

        final long hores = durada / segonsPerHora;
        final long minuts = (durada - hores * segonsPerHora) / segonsPerMinut;
        final long segons = durada - segonsPerHora * hores - segonsPerMinut * minuts;

        return String.valueOf(hores + "h " + minuts + "m " + segons + "s");
    }

	@Override
	public void update(Observable o, Object date) {
		if (isOpen){
			this.setEndWorkingLogDatee((Date) date);
			mCallback.update(this);
		}
	}
}
