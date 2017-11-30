import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import model.Item;
import model.Project;
import model.Task;

import reports.BriefReport;
import reports.DetailedReport;

import utils.ItemsTreeManager;
import utils.Printer;

public class Main {

  // Menu constants.
  private static final int RUN_TEST_1 = 1;
  private static final int RUN_TEST_2 = 2;
  private static final int RESET_TREE = 3;
  private static final int RUN_TEST_3 = 4;
  private static final int GENERATE_DETAILED_REPORT = 5;
  private static final int GENERATE_BRIEF_REPORT = 6;

  private static final String FORMAT = "html";

  private static ArrayList<Item> items; // Main items of the tree, coming from node 0.

  /**
   * Show main menu and initialize a item tree.
   * @param args Parameters for execution.
   */
  public static void main(String[] args) {

    // We ask the file to return saved items.
    items = ItemsTreeManager.getItems();

    // If we don't get any items from file, we set a new empty tree and then we save it in the file.
    if (items.size() == 0) {
      items = ItemsTreeManager.setTreeOfFita2();
      ItemsTreeManager.saveItems(items);
    }

    // Once we have loaded the list of main items we show a menu to the user and ask for an action.
    showMenu();
  }

  private static void showMenu() {
    Scanner reader = new Scanner(System.in); // Reading from System.in.

    // Display menu options in the console.
    System.out.println("Select an action:");
    System.out.println("  1.Run task test");
    System.out.println("  2.Run simultaneous tasks test");
    System.out.println("  3.Reset tree");
    System.out.println("---------- FITA 2 ------------");
    System.out.println("  4.Run test fita 2");
    System.out.println("  5.Generate Detailed Report");
    System.out.println("  6.Generate Brief Report");
    System.out.println("Enter a number: ");

    int menuOption = reader.nextInt(); // Scans the next token of the input as an integer.

    setMenuAction(menuOption);

    reader.close(); // Stops scanning the console.
  }

  private static void setMenuAction(int menuOption) {
    switch (menuOption) {
      case RUN_TEST_1:
        new Printer(items).printTable(); // Print the table periodically.
        simulateUserInteraction1();
        break;
      case RUN_TEST_2:
        new Printer(items).printTable(); // Print the table periodically.
        simulateUserInteraction2();
        break;
      case RESET_TREE:
        ItemsTreeManager.resetItems();
        main(null); // Reopen the project after the items tree is reset.
        break;
      case RUN_TEST_3:
        simulateUserInteractionFeature2();
        showMenu();
        break;
      case GENERATE_DETAILED_REPORT:
        try {
          new DetailedReport(items, FORMAT);
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        }
        showMenu();
        break;
      case GENERATE_BRIEF_REPORT:
        try {
          new BriefReport(items, FORMAT);
        } catch (IllegalArgumentException | NullPointerException e) {
          e.printStackTrace();
        }
        showMenu();
        break;
      default:
        new Printer(items); // Print the table periodically.
        simulateUserInteraction1();
        break;
    }
  }

  /*
   * Generates the test for Feature 2.
   */
  private static void simulateUserInteractionFeature2() {
    Project p1 = ((Project) items.get(0)); // Gets the project1 from the main items list.
    Project p12 = (Project) p1.getItems().get(0);
    Task t1 = (Task) p1.getItems().get(1); // Gets the task1 from the project1 items list.
    Task t4 = (Task) p12.getItems().get(0);

    // Prints the start date in order to filter in the report.
    System.out.print(new Date().toString());

    // Start tasks 1 and 4 and wait 4 seconds.
    t1.start();
    t4.start();
    sleep(30000);

    // Stop t1 and start t2 and wait 6 seconds.
    Task t2 = (Task) p1.getItems().get(2); // Gets the task2 from the project1 items list.
    t1.stop();
    t2.start();
    sleep(75000);

    // Stop t2 and t4, start t3 and wait 4 seconds.
    Project p2 = ((Project) items.get(1));
    Task t3 = (Task) p2.getItems().get(0);
    t2.stop();
    t4.stop();
    t3.start();
    sleep(60000);

    // Stop t3 and start t2 again. Wait 2 seconds.
    t3.stop();
    t2.start();
    sleep(60000);

    // Start t3 again and wait 4 seconds.
    t3.start();
    sleep(30000);

    // Stop t3 and t2.
    t3.stop();
    t2.stop();

    // Prints the end date in order to filter in the report.
    System.out.print(new Date().toString());

    ItemsTreeManager.saveItems(items);
  }

  /*
   * Simulates user interaction with tasks and projects in order to get through with Test1.
   */
  private static void simulateUserInteraction1() {

    Project project1 = ((Project) items.get(0)); // Gets the project1 from the main items list.
    Task task3 = (Task) project1.getItems().get(1); // Gets the task3 from the project1 items list.

    // Starting the task T3 in project P1.
    task3.start();

    // Waiting for three seconds before pausing T3.
    sleep(3000);

    // Pause the task T3 in project P1.
    task3.stop();
    ItemsTreeManager.saveItems(items);

    // Here we may have table 1.

    // Wait for 7s until next task start.
    sleep(7000);

    // Start the task T2 in subproject P2.
    Project project2 = (Project) project1.getItems().get(0); // Gets the project2 from the project1.
    Task task2 = (Task) project2.getItems().get(1); // Gets the task2 from the project2 items list.
    task2.start();

    // Wait for 10 seconds before pausing T2.
    sleep(10500);

    // Pause the task T2 in subproject P2.
    task2.stop();
    ItemsTreeManager.saveItems(items);

    // Start T3 again.
    task3.start();

    // Wait 2s before pausing T3.
    sleep(2100);

    // Pause T3.
    task3.stop();
    ItemsTreeManager.saveItems(items);

  }

  /*
   * Simulates user interaction with tasks and projects in order to get through with Test2.
   */
  private static void simulateUserInteraction2() {

    Project project1 = ((Project) items.get(0)); // Gets the project1 from the main items list.
    Task task3 = (Task) project1.getItems().get(1); // Gets the task3 from the project1 items list.
    Project project2 = (Project) project1.getItems().get(0); // Gets the project2 from the project1.
    Task task2 = (Task) project2.getItems().get(1); // Gets the task2 from the project2 items list.

    // Starting the task T3 in project P1.
    task3.start();

    // Waiting for 4s before starting T2.
    sleep(4100);

    // Start the task T2 in subproject P2.
    task2.start();
    ItemsTreeManager.saveItems(items);

    // Waiting for 2s before pausing T3.
    sleep(2100);

    // Pause the task T3 in project P1.
    task3.stop();
    ItemsTreeManager.saveItems(items);

    // Waiting for 2s before starting T1.
    sleep(2100);

    // Start the task T1 in subproject P2.
    Task task1 = (Task) project2.getItems().get(0); // Gets the task1 from the project2 items list.
    task1.start();

    // Wait for 4s before pausing T1.
    sleep(4100);

    // Pause the task T1
    task1.stop();
    ItemsTreeManager.saveItems(items);

    // Wait for 2s before pausing T2.
    sleep(2100);

    // Pause the task T2.
    task2.stop();
    ItemsTreeManager.saveItems(items);

    // Wait for 4s before starting T3 again.
    sleep(4100);

    // Starting T3 again
    task3.start();

    // Waiting for 2s before pausing T3.
    sleep(2100);

    // Pausing T3 and finishing the test.
    task3.stop();
    ItemsTreeManager.saveItems(items);

  }

  /*
   * This function is used to sleep the thread
   * @param millis Time in seconds.
   */
  private static void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
