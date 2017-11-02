package model;

import utils.Constants;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class Project extends Item {

	private static final long serialVersionUID = 1L;//Needed object identifier

	private static Logger logger = (Logger) LoggerFactory.getLogger(Interval.class);

	private Project project;

    private ArrayList<Item> items;

    public Project(String name, String description, Project project) {
    	logger.setLevel(Constants.LOGGER_LEVEL);
        this.name = name;
        logger.debug("Project name set as: " + this.name);
        this.description = description;
        logger.debug("Project description set as: " + this.description);
        this.period = new Period();
        this.items = new ArrayList<Item>();

        this.project = project; //callback to update the father
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
        logger.debug("Project " + this.getName() + "'s items set as: " + items);
    }

    public void newTask(String name, String description) {
        items.add(new Task(name, description, this, false, false));
        logger.debug("Task " + name + " added to project " + this.getName());
    }

    public void newProject(String name, String description) {
        items.add(new Project(name, description, this));
        logger.debug("Project " + name + " added to project " + this.getName());
    }
    
    public void start(){
    	//The first time we start a task we set it's start working date and it will never be updated
        if (period.getDuration() == 0) this.period.setStartWorkingDate(new Date());
		this.isOpen = true;
    }
    
    public void stop(){
		//Every time we stop an item inside the project, we update the finalWorkingDate
        this.period.setFinalWorkingDate(new Date());
        this.isOpen = false;
    }
    
    public void update(Item item){
        period.addDuration(Constants.CLOCK_SECONDS);

        if (project != null) {
            project.update(this);//updating the father
        }
    }
}

