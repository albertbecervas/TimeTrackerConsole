package model;

import callback.IntervalCallback;
import callback.ItemCallback;
import observable.Clock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task extends Item implements Serializable, IntervalCallback {

    private ItemCallback mCallback;

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Interval> intervals;

    private boolean isLimited;
    private boolean isProgrammed;
    private long maxDuration;

    public Task(String name, String description, ItemCallback taskCallback, boolean isLimited, boolean isProgrammed) {
        this.name = name;
        this.description = description;
        this.period = new Period();
        this.isOpen = false;
        this.intervals = new ArrayList<>();

        this.isLimited = isLimited;
        this.isProgrammed = isProgrammed;
        this.maxDuration = 120L;

        this.mCallback = taskCallback;
    }

    public void setInterval(Date startDate, Date endDate) {
        intervals.add(new Interval(this));
    }

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public boolean isLimited() {
        return isLimited;
    }

    public void setLimited(boolean limited) {
        isLimited = limited;
    }

    public boolean isProgrammed() {
        return isProgrammed;
    }

    public void setProgrammed(boolean programmed) {
        isProgrammed = programmed;
    }

    public long getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(long maxDuration) {
        this.maxDuration = maxDuration;
    }


    public void start() {
        if (period.getDuration() == 0) this.period.setStartWorkingDate(new Date());
        this.isOpen = true;
        mCallback.started();
        setInterval(new Date(), new Date());
    }

    public void stop() {
        this.isOpen = false;
        this.period.setFinalWorkingDate(new Date());
        mCallback.stopped();
        Interval interval = this.intervals.get(intervals.size() - 1);
        interval.setOpen(false);
    }


    @Override
    public void update(Interval interval) {

        if (isLimited()) {
            if (period.getDuration() <= maxDuration) {
                period.addDuration(Clock.CLOCK_SECONDS);
            }
        } else {
            period.addDuration(Clock.CLOCK_SECONDS);
        }

        mCallback.update(this);
    }
}


