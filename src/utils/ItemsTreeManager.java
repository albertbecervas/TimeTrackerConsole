package utils;

import model.Interval;
import model.Item;
import model.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * This class is used to manage the persistence of the items tree
 */
public abstract class ItemsTreeManager {
    private static Logger logger = (Logger) LoggerFactory.getLogger(Interval.class);

    private static String fileName = "itemsTree.dat"; //name of the file where the items tree is saved

    /**
     * Sets the basic given items tree in order to run the tests
     * @return ArrayList<Item>
     */
    public static ArrayList<Item> setTree() {
    	logger.setLevel(Constants.LOGGER_LEVEL);
        ArrayList<Item> items = new ArrayList<Item>();

        Project project = new Project("P1", "first project from node 0", null);
        project.newProject("P2", "subproject of P1");
        project.newTask("T3", "task 3 in project P1");

        //in order to set a task into a project that is inside another project, we have to iterate within it.
        ((Project) project.getItems().get(0)).newTask("T1", "sub sub task1 from P2");
        ((Project) project.getItems().get(0)).newTask("T2", "sub sub task2 from P2");

        //once we have all the project items set, we add the project on the main list
        items.add(project);

        return items;
    }

    /**
     * Gets the items form the specified file and puts them on ArrayList
     * @return ArrayList<Item> 
     */
    public static ArrayList<Item> getItems() {
        File file = new File(fileName);

        ArrayList<Item> items = new ArrayList<>();

        //if the file doesn't exists this function will return an empty array
        if (file.exists()) {
            // read object from file
            try {
            	logger.debug("Loading items.");
                FileInputStream fips = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fips);
                items = (ArrayList<Item>) in.readObject();
                in.close();
            } catch (IOException | ClassNotFoundException e) {
            	logger.error("Failed trying to load items.");
                e.printStackTrace();
            }
        }
        return items;
    }

    /**
     * Saves the items tree in a file in order to have persistence in the application
     * @param items
     */
    public static void saveItems(ArrayList<Item> items) {

        // write object to file
        try {
        	logger.debug("Saving items.");
            FileOutputStream fops = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fops);
            out.writeObject(items);
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	logger.error("Failed trying to save items.");
            e.printStackTrace();
        }
    }

    /**
     * Deletes the file where the tree is saved
     */
    public static void resetItems() {
        File file = new File(fileName);
        if (file.exists()) {
        	logger.debug("File " + fileName + " already exists.");
            file.delete();
            logger.debug("File " + fileName + " deleted.");
        }
    }

}

