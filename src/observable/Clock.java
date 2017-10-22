package observable;

import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {

    public static final int CLOCK_SECONDS = 2;

    private static Clock instance = null;

    private Clock() {
        Observable observer = this;
        Thread thread = new Thread((Clock) observer);
        Timer timer = new Timer();
        timer.schedule(thread, new Date(), CLOCK_SECONDS * 1000);
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
        }
    }

}

