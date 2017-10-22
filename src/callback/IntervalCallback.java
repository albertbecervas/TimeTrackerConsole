package callback;

import model.Interval;

/**
 *The IntervalCallback must be implemented in those classes that want to listen on the Interval class.
 * In TimeTracker case, we must implement this interface in Task Class in order to know when an
 * Interval has been updated.
 *
 * All classes that implements this interface must implement the update() method.
 */
public interface IntervalCallback {

    /**
     * This method should be called when an interval is updated
     * @param interval
     */
    void update(Interval interval);

}
