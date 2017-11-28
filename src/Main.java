
import model.Item;
import model.Project;
import model.Task;
import utils.ItemsTreeManager;
import utils.Printer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    //Menu constants
    private static final int RUN_TEST_1 = 1;
    private static final int RUN_TEST_2 = 2;
    private static final int RESET_TREE = 3;
    private static final int CREATE_PROJECT = 4;
    private static final int CREATE_TASK = 5;
    private static final int GENERATE_BRIEF_REPORT = 7;

    private static ArrayList<Item> items; //Main items of the tree, coming from node 0

    public static void main(String[] args) {

        //We ask the file to return saved items
        //items = ItemsTreeManager.getItems();

        //if we don't get any items from file, we set the a new empty tree and then we save it in the file
        /*if (items.size() == 0) {
            items = ItemsTreeManager.setTree();
            ItemsTreeManager.saveItems(items);
        }*/

    	items = ItemsTreeManager.getItems();

        if (items.size() == 0) {
        	items = ItemsTreeManager.setTree2();
        	ItemsTreeManager.saveItems(items);
        }
    	
        //once we have loaded the list of main items we show a menu to the user and ask for an action
        showMenu();

    }

    private static void showMenu() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        //Display menu options in the console
        System.out.println("Select an action:");
        System.out.println("  1.Run task test");
        System.out.println("  2.Run simultaneous tasks test");
        System.out.println("  3.Reset tree");
        System.out.println("  4.Create task");
        System.out.println("  5.Create project");
        System.out.println("  6.Run decorator");
        System.out.println("  7.Generate Detailed Report");
        System.out.println("Enter a number: ");

        int menuOption = reader.nextInt(); // Scans the next token of the input as an integer.

        setMenuAction(menuOption);
        
        reader.close(); //Stops scanning the console
    }

    private static void setMenuAction(int menuOption) {
        switch (menuOption) {
            case RUN_TEST_1:
                /*new Printer(items).printTable(); //start using the class Printer in order to print the table periodically
                simulateUserInteraction1();*/
            	generateBriefReport();
            	showMenu();
                break;
            case RUN_TEST_2:
                new Printer(items).printTable(); //start using the class Printer in order to print the table periodically
                simulateUserInteraction2();
                break;
            case RESET_TREE:
                ItemsTreeManager.resetItems();
                main(null); //reopen the project after the items tree is reset
                break;
            case CREATE_PROJECT:
                break;
            case CREATE_TASK:
                break;
            case GENERATE_BRIEF_REPORT:
            	//generateBriefReport();
            	new Printer(items).generateBriefReport();
            	break;
            default:
                new Printer(items); //start using the class Printer in order to print the table periodically
                simulateUserInteraction1();
                break;
        }
    }
    
    /**
     * Generates a brief report
     */
    private static void generateBriefReport(){
    	Project p1 = ((Project) items.get(0)); //gets the project1 from the main items list
    	Project p12 = (Project) p1.getItems().get(0);
    	Task t1 =(Task) p1.getItems().get(1);//gets the task1 from the project1 items list
    	Task t2 =(Task) p1.getItems().get(2);//gets the task2 from the project1 items list
    	Task t4 = (Task) p12.getItems().get(0);
    	
    	Project p2 = ((Project) items.get(1));
    	Task t3 = (Task) p2.getItems().get(0);
    	
    	System.out.print(new Date().toString());
    	
    	
    	//start tasks 1 and 4 and wait 4 seconds
    	t1.start();
    	t4.start();
    	sleep(4000);
    	
    	//stop t1 and start t2 and wait 6 seconds
    	t1.stop();
    	t2.start();
    	sleep(6000);
    	
    	//stop t2 and t4, start t3 and wait 4 seconds
    	t2.stop();
    	t4.stop();
    	t3.start();
    	sleep(4000);
    	
    	//stop t3 and start t2 again. Wait 2 seconds
    	t3.stop();
    	t2.start();
    	sleep(2000);
    	
    	//start t3 again and wait 4 seconds 
    	t3.start();
    	sleep(4000);
    	
    	//stop t3 and t2.
    	t3.stop();
    	t2.stop();
    	
    	System.out.print(new Date().toString());
    	
        ItemsTreeManager.saveItems(items);
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

    /**
     * This function is used to sleep the thread
     * @param millis
     */
    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

