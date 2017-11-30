package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Item;
import model.Project;

/**
 * This class is used to manage the persistence of the items tree
 * @version 2.00, 29/11/2017
 */
public abstract class ItemsTreeManager {

  private static String fileName = "itemsTree.dat"; // File's name where item tree is saved.

  /**
   * Sets the basic given items tree in order to run the tests.
   * @return ArrayList List of the items.
   */
  public static ArrayList<Item> setTree() {
    ArrayList<Item> items = new ArrayList<Item>();

    Project project = new Project("P1", "first project from node 0", null);
    try {
      project.newProject("P2", "subproject of P1");
      project.newTask("T3", "task 3 in project P1");

      // in order to set a task into a project that is inside another project, we have
      // to iterate within it.
      ((Project) project.getItems().get(0)).newTask("T1", "sub sub task1 from P2");
      ((Project) project.getItems().get(0)).newTask("T2", "sub sub task2 from P2");
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    // once we have all the project items set, we add the project on the main list
    items.add(project);

    return items;
  }

  /**
   * Create a tree of test
   * @return items List of projects or tasks.
   */
  public static ArrayList<Item> setTreeOfFita2() {
    ArrayList<Item> items = new ArrayList<Item>();

    Project p1 = new Project("P1", "p1", null);
    try {
      p1.newProject("P1.2", " subproject from p1");
      p1.newTask("T1", "task from p1");
      p1.newTask("T2", "task from p1");

      ((Project) p1.getItems().get(0)).newTask("T4", "task from p1.2");
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    Project p2 = new Project("P2", "p2", null);

    try {
      p2.newTask("T3", "task from p2");
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    items.add(p1);
    items.add(p2);
    return items;
  }

  /**
   * Gets the items form the specified file and puts them on ArrayList.
   * @return ArrayList List of the items.
   */
  @SuppressWarnings("unchecked")
  public static ArrayList<Item> getItems() {
    File file = new File(fileName);

    ArrayList<Item> items = new ArrayList<>();

    // if the file doesn't exists this function will return an empty array
    if (file.exists()) {
      // read object from file
      try {
        FileInputStream fips = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fips);
        items = (ArrayList<Item>) in.readObject();
        in.close();
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return items;
  }

  /**
   * Saves the items tree in a file in order to have persistence in the
   * application.
   * @param items Projects or tasks in items tree.
   */
  public static void saveItems(ArrayList<Item> items) {

    // write object to file
    try {
      FileOutputStream fops = new FileOutputStream(fileName);
      ObjectOutputStream out = new ObjectOutputStream(fops);
      out.writeObject(items);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes the file where the tree is saved.
   */
  public static void resetItems() {
    File file = new File(fileName);
    if (file.exists()) {
      file.delete();
    }
  }

}
