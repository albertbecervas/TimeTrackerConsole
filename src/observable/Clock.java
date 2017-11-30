package observable;

import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is used for throwing an advise to all Observers that are subscribed
 * every specified period of time.
 * It takes the roll of a clock in a parallel thread of the main application.
 * @invariant CLOCK_SECONDS > 0
 */
public class Clock extends Observable {

  public static final int CLOCK_SECONDS = 1;

  private static Clock instance = null;

  private Clock() {
    Timer timer = new Timer();
    timer.schedule(new Thread(this), new Date(), CLOCK_SECONDS * 1000); // Schedule the time (ms).
  }

  /**
   * Allows to instantiate only one time in runtime.
   * @return Clock Clock that counts the seconds.
   */
  public static Clock getInstance() {
    if (instance == null) {
      return new Clock();  
    }
    return instance;
  }

  /*
   * In charge of running the clock in a secondary thread every specified time.
   */
  private class Thread extends TimerTask {

    private Clock observable;

    private Thread(Clock clock) {
      observable = clock;
    }

    /*
     * This function is called periodically whether it has subscribers or not
     */
    @Override
    public void run() {
      observable.setChanged();
      observable.notifyObservers(new Date());
    }
  }
}
