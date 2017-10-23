package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import model.Item;
import model.Project;
import model.Task;
import observable.Clock;

public class Printer implements Observer {

    private ArrayList<Item> items;
    
    private static Logger logger = (Logger) LoggerFactory.getLogger(Printer.class);

    public Printer(ArrayList<Item> items) {
    	logger.setLevel(Constants.LOGGER_LEVEL);
        Clock.getInstance().addObserver(this);
        this.items = items;
    }

    public void printTable() throws IOException {
        System.out.flush();
        logger.trace("Printing time table.");
        print("\r \n\n TIME TABLE:\n");
        for (Item item : items) {
            recursive(item, "");
        }
    }


    public void recursive(Item item, String tabs) {
        if (item instanceof Task) {
            print(tabs + item.getFormattedTable());
        } else {
            print(tabs + item.getFormattedTable());
            tabs = tabs + "   -";
            for (Item subItem : ((Project) item).getItems()) {
                recursive(subItem, tabs);
            }
        }
    }

    public void print(String text) {
        System.out.print(text);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            printTable();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	logger.error("Error printing table.");
            e.printStackTrace();
        }

    }

}
