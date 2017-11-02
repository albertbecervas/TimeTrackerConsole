package utils;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import model.Interval;
import model.Item;
import model.Project;
import model.Task;
import observable.Clock;

/**
 * Prints the items tree in a formatted table every period of time.
 */
public class Printer implements Observer {
    private static Logger logger = (Logger) LoggerFactory.getLogger(Interval.class);

    private ArrayList<Item> items;

    public Printer(ArrayList<Item> items) {
    	logger.setLevel(Level. INFO);
        Clock.getInstance().addObserver(this); //We need the clock in order to repeat the print of the table periodically
        this.items = items;
    }

    /**
     * Prints the items table in console in a formatted way
     */
    public void printTable(){
    	logger.trace("Printing time table.");
        print("\r \n\n TIME TABLE:\n");
        for (Item item : items) {
            recursivePrint(item, "");
        }
    }

    /**
     * Goes through all tree in order to print every item inside it
     * @param item
     * @param tabs
     */
    public void recursivePrint(Item item, String tabs) {
        if (item instanceof Task) {
            print(tabs + item.getFormattedTable());
        } else {
            print(tabs + "-" + item.getFormattedTable());
            tabs = tabs + "   "; //leaves some space in order to see in which item level are we
            for (Item subItem : ((Project) item).getItems()) {
                recursivePrint(subItem, tabs);
            }
        }
    }

    public void print(String text) {
        System.out.print(text);
    }

    @Override
    public void update(Observable o, Object arg) {
        printTable();
    }

}
