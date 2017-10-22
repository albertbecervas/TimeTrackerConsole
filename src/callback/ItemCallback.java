package callback;

import model.Item;

/**
 *The ItemCallback must be implemented in those classes that want to listen on the Task class.
 * In TimeTracker case, we must implement this interface in Project Class in order to know when a
 * Task has been updated.
 *
 * All classes that implements this interface must implement the update() method.
 */
public interface ItemCallback {

    /**
     * This method should be called when a task is updated
     * @param item
     */
    void update(Item item);

}
