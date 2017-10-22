package model;

import callback.ItemCallback;
import observable.Clock;

import java.util.ArrayList;
import java.util.Date;

public class Project extends Item implements ItemCallback {

    private ItemCallback itemCallback;

    private ArrayList<Item> items;

    public Project(String name, String description, Project project) {
        this.name = name;
        this.description = description;
        this.period = new Period();
        this.items = new ArrayList<Item>();

        this.itemCallback = project;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void newTask(String name, String description) {
        Task task = new Task(name, description, this, false, false);
        items.add(task);
    }

    public void newProject(String name, String description) {
        Project project = new Project(name, description, this);
        items.add(project);
    }

    public void startTask(int position) {
        if (period.getDuration() == 0) this.period.setStartWorkingDate(new Date());
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
            itemCallback.update(this);
        }
    }

}

