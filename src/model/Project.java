package model;

import callback.ItemCallback;
import ch.qos.logback.classic.Logger;
import utils.Constants;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.LoggerFactory;

public class Project extends Item implements ItemCallback {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	private ItemCallback itemCallback;

    private ArrayList<Item> items;

    private static Logger logger = (Logger) LoggerFactory.getLogger(Project.class);

    public Project(String name, String description, Project project) {
        logger.setLevel(Constants.LOGGER_LEVEL);
    	
    	this.name = name;
        logger.debug("Project name set as: " + this.name);
        this.description = description;
        logger.debug("Project description set as: " + this.description);
        this.period = new Period();
        this.items = new ArrayList<Item>();

        this.itemCallback = project;
    }

    public ArrayList<Item> getItems() {
    	return items;
    }

    public void setItems(ArrayList<Item> items) {
    	logger.debug("Project " + this.getName() + "'s items set as:" + items);
        this.items = items;
    }

    public void newTask(String name, String description) {
        Task task = new Task(name, description, this, false, false);
        items.add(task);
        logger.debug("Task " + task.name + " added to project " + this.getName());
    }

    public void newProject(String name, String description) {
        Project project = new Project(name, description, this);
        items.add(project);
        logger.debug("Project " + project.name + " added to project " + this.getName());
    }

    public void startTask(int position) {
        if (period.getDuration() == 0) this.period.setStartWorkingDate(new Date());
        ((Task) items.get(position)).start();
        this.isOpen = true;
        logger.trace("Task " + this.getName() + " started.");
    }

    public void stopTask(int position) {
        ((Task) items.get(position)).stop();
        this.period.setFinalWorkingDate(new Date());
        this.isOpen = false;
        logger.trace("Task " + this.getName() + " stopped.");
    }


    @Override
    public void update(Item item) {
        period.addDuration(Constants.CLOCK_SECONDS);

        if (itemCallback != null) {
            itemCallback.update(this);
        }
    }

}

