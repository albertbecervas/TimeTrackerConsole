package observable;

import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is used for throwing an advise to all Observers that are subscribed
 * every specified period of time
 * 
 * It takes the roll of a clock in a parallel thread of the main application
 */
public class Clock extends Observable {

    public static final int CLOCK_SECONDS = 1; 

    private static Clock instance = null;

    private Clock() {
        Timer timer = new Timer();
        timer.schedule(new Thread(this), new Date(), CLOCK_SECONDS * 1000); //schedule the time in mills.
    }

    /**
     * Allows to instantiate only one time in runtime 
     * @return Clock
     */
    public static Clock getInstance() {
        if (instance == null) return new Clock();
        return instance;
    }

    /**
     * In charge of running the clock in a secondary thread every specified time
     *
     */
    private class Thread extends TimerTask {

        private Clock mObservable;

        private Thread(Clock observable) {
            mObservable = observable;
        }

        /**
         * This function is called periodically whether it has subscribers or not
         */
        @Override
        public void run() {
            mObservable.setChanged();
            mObservable.notifyObservers(new Date());
        }
    }
}

