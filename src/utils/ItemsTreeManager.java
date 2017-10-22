package utils;

import model.Item;
import model.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This class is used to manage the persistence of the items tree
 */
public abstract class ItemsTreeManager {

    private static String fileName = "itemsTree.dat";

    public static ArrayList<Item> setTree() {
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

    @SuppressWarnings("unchecked")
    public static ArrayList<Item> getItems() {
        File file = new File(fileName);

        ArrayList<Item> items = new ArrayList<>();

        if (file.exists()) {
            // read object from file
            try {
                FileInputStream fips = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fips);
                items = (ArrayList<Item>) in.readObject();
                in.close();
            } catch (IOException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return items;
    }

    public static void saveItems(ArrayList<Item> items) {

        // write object to file
        try {
            FileOutputStream fops = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fops);
            out.writeObject(items);
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void resetItems() {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

}

