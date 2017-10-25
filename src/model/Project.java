package model;

import observable.Clock;

import java.util.ArrayList;
import java.util.Date;

public class Project extends Item {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Project project;

    private ArrayList<Item> items;

    public Project(String name, String description, Project project) {
        this.name = name;
        this.description = description;
        this.period = new Period();
        this.items = new ArrayList<Item>();

        this.project = project; //callback to update the father
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
        period.addDuration(Clock.CLOCK_SECONDS);

        if (project != null) {
            project.update(this);//updating the father
        }
    }
}

