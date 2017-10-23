

import model.Item;
import model.Project;
import utils.Constants;
import utils.ItemsTreeManager;
import utils.Printer;

import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;


public class Main {

    private static ArrayList<Item> items; //Main items of the tree, coming from node 0

    private static Logger logger = (Logger) LoggerFactory.getLogger(Main.class);
	
    public static void main(String[] args) {
    	
    	//We set the logback filter level
    	logger.setLevel(Constants.LOGGER_LEVEL);
    	
    	logger.trace("Program started");
    	

        //We ask the file to return saved items
        items = ItemsTreeManager.getItems();

        //if we don't get any items from file, we set the a new empty tree and then we save it in the file
        if (items.isEmpty()) {
            items = ItemsTreeManager.setTree();
            ItemsTreeManager.saveItems(items);
            logger.debug("There are no items, new empty tree set.");
        }

        //once we have loaded the list of main items we show a menu to the user and ask for an action
        showMenu();

    }

    private static void showMenu() {
    	logger.trace("Printing menu.");
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        //Display menu options in the console
        System.out.println("Select an action:");
        System.out.println("  1.Run task test");
        System.out.println("  2.Run simultaneous tasks test");
        System.out.println("  3.Reset tree");
        System.out.println("  4.Create task");
        System.out.println("  5.Create project");
        System.out.println("Enter a number: ");

        int menuOption = reader.nextInt(); // Scans the next token of the input as an int.
        logger.info("Menu option selected: " + menuOption);

        setMenuAction(menuOption);
        
        reader.close(); //Stops scanning the console
        logger.trace("Console scanning stopped");
    }

    private static void setMenuAction(int menuOption) {
        switch (menuOption) {
            case Constants.RUN_TEST_1:
            	logger.debug("Inside option 1.");
                new Printer(items); //start using the class Printer in order to print the table periodically
                simulateUserInteraction1();
                break;
            case Constants.RUN_TEST_2:
            	logger.debug("Inside option 2.");
                //start using the class Printer in order to print the table every specified time
                new Printer(items); //start using the class Printer in order to print the table periodically
                simulateUserInteraction2();
                break;
            case Constants.RESET_TREE:
            	logger.debug("Inside option 3.");
                ItemsTreeManager.resetItems();
                showMenu(); //once we've reset the items we show the menu again
                break;
            case Constants.CREATE_PROJECT:
            	logger.debug("Inside option 4.");
                break;
            case Constants.CREATE_TASK:
            	logger.debug("Inside option 5.");
                break;
            default:
            	logger.debug("Inside option default.");
                new Printer(items); //start using the class Printer in order to print the table periodically
                simulateUserInteraction1();
                break;
        }
    }

    /**
     * Simulates user interaction with tasks and projects in order to get through with Test1
     */
    private static void simulateUserInteraction1() {

        //starting the task T3 in project P1
        ((Project) items.get(0)).startTask(1);

        //waiting for three seconds before pausing T3
        sleep(3000);

        //pause the task T3 in project P1
        ((Project) items.get(0)).stopTask(1);

        ItemsTreeManager.saveItems(items);

        //here we may have table 1.

        //wait for 7s until next task start
        sleep(7000);

        //start the task T2 in subproject P2
        ((Project) ((Project) items.get(0)).getItems().get(0)).startTask(1);

        //wait for 10 seconds before pausing T2
        sleep(10500);

        //pause the task T2 in subproject P2
        ((Project) ((Project) items.get(0)).getItems().get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);

        //start T3 again
        ((Project) items.get(0)).startTask(1);

        //wait 2s before pausing T3
        sleep(2100);

        //pause T3
        ((Project) items.get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);

    }

    /**
     * Simulates user interaction with tasks and projects in order to get through with Test2
     */
    private static void simulateUserInteraction2() {
        //starting the task T3 in project P1
        ((Project) items.get(0)).startTask(1);

        //waiting for 4s before starting T2
        sleep(4100);

        //start the task T2 in subproject P2
        ((Project) ((Project) items.get(0)).getItems().get(0)).startTask(1);
        ItemsTreeManager.saveItems(items);

        //waiting for 2s before pausing T3
        sleep(2100);

        //pause the task T3 in project P1
        ((Project) items.get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);

        //waiting for 2s before starting T1
        sleep(2100);

        //start the task T1 in subproject P2
        ((Project) ((Project) items.get(0)).getItems().get(0)).startTask(0);

        //wait for 4s before pausing T1
        sleep(4100);

        //pause the task T1
        ((Project) ((Project) items.get(0)).getItems().get(0)).stopTask(0);
        ItemsTreeManager.saveItems(items);

        //wait for 2s before pausing T2
        sleep(2100);

        //pause the task T2
        ((Project) ((Project) items.get(0)).getItems().get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);
        
        //wait for 4s before starting T3 again
        sleep(4100);

        //starting T3 again
        ((Project) items.get(0)).startTask(1);

        //waiting for 2s before pausing T3
        sleep(2100);

        //pausing T3 and finishing the test
        ((Project) items.get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);

    }

    /**
     * This function is used to sleep the thread
     * @param millis
     */
    private static void sleep(int millis) {
        try {
            logger.trace("Staring " + millis +  " milliseconds sleep.");
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("Sleep failed.");
        }
    }

}

