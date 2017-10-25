package model;

import callback.ItemCallback;
import observable.Clock;

import java.util.ArrayList;
import java.util.Date;

public class Project extends Item implements ItemCallback {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ItemCallback itemCallback;

    private ArrayList<Item> items;

    public Project(String name, String description, Project project) {
        this.name = name;
        this.description = description;
        this.period = new Period();
        this.items = new ArrayList<Item>();

        this.itemCallback = project; //callback to update the father
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void newTask(String name, String description) {
        items.add(new Task(name, description, this, false, false));
    }

    public void newProject(String name, String description) {
        items.add(new Project(name, description, this));
    }

    public void startTask(int position) {
    	//The first time we start a task we set it's start working date and it will never be updated
        if (period.getDuration() == 0) this.period.setStartWorkingDate(new Date());
        //TODO in future, we will only be able to start a task, that's why we can cast every 
        //selected item to Task. Otherwise it may produce an error if the item is a project
        ((Task) items.get(position)).start(); 
        this.isOpen = true;
    }

    public void stopTask(int position) {
        ((Task) items.get(position)).stop();
        this.period.setFinalWorkingDate(new Date());
        this.isOpen = false;
    }


    @Override
    public void update(Item item) {
        period.addDuration(Clock.CLOCK_SECONDS);

        if (itemCallback != null) {
            itemCallback.update(this); //updating the father
        }
    }

}

