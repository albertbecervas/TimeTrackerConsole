
import model.Interval;
import model.Item;
import model.Project;
import model.Task;
import utils.Constants;
import utils.ItemsTreeManager;
import utils.Printer;

import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class Main {

    //Menu constants

    private static Logger logger = (Logger) LoggerFactory.getLogger(Interval.class);

    private static ArrayList<Item> items; //Main items of the tree, coming from node 0

    public static void main(String[] args) {
    	logger.setLevel(Constants.LOGGER_LEVEL);
    	logger.info("Program started.");

        //We ask the file to return saved items
        items = ItemsTreeManager.getItems();

        //if we don't get any items from file, we set the a new empty tree and then we save it in the file
        if (items.size() == 0) {
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
        System.out.println("  6.Run decorator");
        System.out.println("Enter a number: ");

        int menuOption = reader.nextInt(); // Scans the next token of the input as an integer.
        logger.info("Menu option selected: " + menuOption);

        setMenuAction(menuOption);

        reader.close(); //Stops scanning the console
        logger.trace("Console scanning stopped.");
    }

    private static void setMenuAction(int menuOption) {
        switch (menuOption) {
            case Constants.RUN_TEST_1:
            	logger.debug("Starting test 1.");
                new Printer(items); //start using the class Printer in order to print the table periodically
                simulateUserInteraction1();
                break;
            case Constants.RUN_TEST_2:
            	logger.debug("Starting test 2.");
                new Printer(items); //start using the class Printer in order to print the table periodically
                simulateUserInteraction2();
                break;
            case Constants.RESET_TREE:
            	logger.debug("Reseting tree.");
                ItemsTreeManager.resetItems();
                main(null); //reopen the project after the items tree is reset
                break;
            case Constants.RUN_DECORATOR:
            	logger.debug("Running decorator.");
            	new Printer(runDecoratorTest());
            	break;
            case Constants.CREATE_PROJECT:
            	logger.debug("Creating project.");
                break;
            case Constants.CREATE_TASK:
            	logger.debug("Creating task.");
                break;
            default:
            	logger.info("This option does not exist.");
                new Printer(items); //start using the class Printer in order to print the table periodically
                simulateUserInteraction1();
                break;
        }
    }

    /**
     * Simulates user interaction with tasks and projects in order to get through with Test1
     */
    private static void simulateUserInteraction1() {
    	
    	Project project1 = ((Project) items.get(0)); //gets the project1 from the main items list
    	Task task3 =(Task) project1.getItems().get(1);//gets the task3 from the project1 items list
    	Project project2 = (Project) project1.getItems().get(0);//gets the project2 from the project1 items list
    	Task task2 = (Task) project2.getItems().get(1);//gets the task2 from the project2 items list

        //starting the task T3 in project P1
        task3.start();

        //waiting for three seconds before pausing T3
        sleep(3000);

        //pause the task T3 in project P1
        task3.stop();
        ItemsTreeManager.saveItems(items);

        //here we may have table 1.
       
        //wait for 7s until next task start
        sleep(7000);

        //start the task T2 in subproject P2
        task2.start();

        //wait for 10 seconds before pausing T2
        sleep(10500);

        //pause the task T2 in subproject P2
        task2.stop();
        ItemsTreeManager.saveItems(items);

        //start T3 again
        task3.start();

        //wait 2s before pausing T3
        sleep(2100);

        //pause T3
        task3.stop();
        ItemsTreeManager.saveItems(items);

    }

    
    /**
     * Simulates user interaction with tasks and projects in order to get through with Test2
     */
    private static void simulateUserInteraction2() {
    	
    	Project project1 = ((Project) items.get(0)); //gets the project1 from the main items list
    	Task task3 =(Task) project1.getItems().get(1);//gets the task3 from the project1 items list
    	Project project2 = (Project) project1.getItems().get(0);//gets the project2 from the project1 items list
    	Task task1 = (Task) project2.getItems().get(0);//gets the task1 from the project2 items list
    	Task task2 = (Task) project2.getItems().get(1);//gets the task2 from the project2 items list
    	
        //starting the task T3 in project P1
        task3.start();

        //waiting for 4s before starting T2
        sleep(4100);

        //start the task T2 in subproject P2
        task2.start();
        ItemsTreeManager.saveItems(items);

        //waiting for 2s before pausing T3
        sleep(2100);

        //pause the task T3 in project P1
        task3.stop();
        ItemsTreeManager.saveItems(items);

        //waiting for 2s before starting T1
        sleep(2100);

        //start the task T1 in subproject P2
        task1.start();

        //wait for 4s before pausing T1
        sleep(4100);

        //pause the task T1
        task1.stop();
        ItemsTreeManager.saveItems(items);

        //wait for 2s before pausing T2
        sleep(2100);

        //pause the task T2
        task2.stop();
        ItemsTreeManager.saveItems(items);

        //wait for 4s before starting T3 again
        sleep(4100);

        //starting T3 again
        task3.start();

        //waiting for 2s before pausing T3
        sleep(2100);

        //pausing T3 and finishing the test
        task3.stop();
        ItemsTreeManager.saveItems(items);

    }

    private static ArrayList<Item> runDecoratorTest(){
    	Task limmitedTask = new Task("limmitedTask", "this is a limmited time task",null,true,false);
    	ArrayList<Item> tasks = new ArrayList<Item>();
    	tasks.add(limmitedTask);
    	limmitedTask.start();
    	
    	return tasks;
    }
    /**
     * This function is used to sleep the thread
     * @param millis
     */
    private static void sleep(int millis) {
        try {
        	logger.trace("Starting " + millis + " milliseconds sleep.");
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            logger.error("Sleep failed.");
        	e.printStackTrace();
        }
    }

}

