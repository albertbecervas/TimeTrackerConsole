package model;

import java.util.ArrayList;
import java.util.Date;

import observable.Clock;

/**
 * @invariant name != null
 * @invariant description != null
 * @version 2.00, 29/11/2017
 */
public class Project extends Item {

  private static final long serialVersionUID = 1L;// Needed object identifier

  // father project
  private Project project;

  private ArrayList<Item> items;

  /**
   * Constructor of Project item.
   * @param name Name of project.
   * @param description Description of project.
   * @param project Project into a new project.
   */
  public Project(String name, String description, Project project) {
    this.name = name;
    this.description = description;
    this.period = new Period();
    this.items = new ArrayList<Item>();

    this.project = project; // callback to update the father
  }

  public ArrayList<Item> getItems() {
    return items;
  }

  public void setItems(ArrayList<Item> items) {
    this.items = items;
  }

  /**
   * Create a new task.
   * @param name Name of new task.
   * @param description Description of new task.
   * @throws IllegalArgumentException Invalid argument.
   */
  public void newTask(String name, String description) throws IllegalArgumentException {
    if (name == null || description == null) {
      throw new IllegalArgumentException("Name nor description can not be null"); 
    }
    items.add(new Task(name, description, this));
  }

  /**
   * Create a new project.
   * @param name Name of new project.
   * @param description Description of new project.
   * @throws IllegalArgumentException Invalid argument.
   */
  public void newProject(String name, String description) throws IllegalArgumentException {
    if (name == null || description == null) {
      throw new IllegalArgumentException("Name nor description can not be null"); 
    }
    items.add(new Project(name, description, this));
  }

  /**
   * Start a new period.
   */
  public void start() {
    // The first time we start a task we set it's start working date and it will
    // never be updated
    if (period.getDuration() == 0) {
      this.period.setStartWorkingDate(new Date()); 
    }
    this.isOpen = true;
  }

  /**
   * Stop a period.
   */
  public void stop() {
    // Every time we stop an item inside the project, we update the finalWorkingDate
    this.period.setFinalWorkingDate(new Date());
    this.isOpen = false;
  }

  /**
   * Update a item period.
   * @param item Task or project.
   */
  public void update(Item item) {
    try {
      period.addDuration(Clock.CLOCK_SECONDS);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    if (project != null) {
      project.update(this);// updating the father
    }
  }
}
