package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import callback.TaskCallback;
import observable.Clock;

public class Project extends Item implements Serializable, TaskCallback {

	private TaskCallback taskCallback; 
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Date initialWorkingDate;
    private Date finalWorkingDate;
    
    private ArrayList<Item> items;

    public Project() {
        this.itemType = "0";
        this.duration = 0L;
        this.items = new ArrayList<Item>();
    }

    public Project(String name, String description, Project project) {
        this.setName(name);
        this.setDescription(description);
        this.items = new ArrayList<Item>();
        this.itemType = "0";
        this.duration = 0L;
        this.taskCallback = project;
    }
    
    public ArrayList<Item> getItems(){
    	return items;
    }
    
    public void setItems(ArrayList<Item> items){
    	this.items = items;
    }
    
    public void newTask(String name, String description){
    	Task task = new Task(name, description, this, false, false);
    	items.add(task);
    }
    
    public void newProject(String name, String description){
    	Project project = new Project(name, description, this);
    	items.add(project);
    }
   

    public Date getInitialWorkingDate() {
        return initialWorkingDate;
    }

    public void setInitialWorkingDate(Date initialWorkingDate) {
        this.initialWorkingDate = initialWorkingDate;
    }

    public Date getFinalWorkingDate() {
        return finalWorkingDate;
    }

    public void setFinalWorkingDate(Date finalWorkingDate) {
        this.finalWorkingDate = finalWorkingDate;
        duration += (getFinalWorkingDate().getTime() - getInitialWorkingDate().getTime()) / 1000;
    }
    
    public void startTask(int position){
    	((Task) items.get(position)).start();
    	this.isOpen = true;
    }
    
    public void stopTask(int position){
    	((Task) items.get(position)).stop();
    	this.isOpen = false;
    }
   

	@Override
	public void update(Item item) {
        addDuration(Clock.CLOCK_SECONDS);
      
        if (taskCallback != null){
        	taskCallback.update(this);
        }
	}

}
