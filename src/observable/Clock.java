package observable;

import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import utils.Constants;

public class Clock extends Observable {

    private static Logger logger = (Logger) LoggerFactory.getLogger(Clock.class);
	
    private static Clock instance = null;

    private Clock() {
    	logger.setLevel(Constants.LOGGER_LEVEL);
        Observable observer = this;
        Thread thread = new Thread((Clock) observer);
        Timer timer = new Timer();
        timer.schedule(thread, new Date(), Constants.CLOCK_SECONDS * 1000);
        logger.debug("Clock unit set as: " + Constants.CLOCK_SECONDS + " seconds.");        
    }

    public static Clock getInstance() {
        if (instance == null) return new Clock();
        return instance;
    }

    class Thread extends TimerTask {

        Clock mObservable;

        Thread(Clock observable) {
            mObservable = observable;
        }

        @Override
        public void run() {
            mObservable.setChanged();
            mObservable.notifyObservers(new Date());
            logger.info("Observers notified.");
        }
    }

}

