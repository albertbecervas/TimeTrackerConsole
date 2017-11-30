package utils;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.Item;
import model.Project;
import model.Task;
import observable.Clock;

/*
 * Prints the items tree in a formatted table every period of time.
 */
public class Printer implements Observer {

  private ArrayList<Item> items;

  public Printer(ArrayList<Item> items) {
    this.items = items;
  }

  /**
   * Prints the items table in console in a formatted way.
   */
  public void printTable() {
    Clock.getInstance().addObserver(this); // Print of the table periodically.
    print("\r \n\n TIME TABLE:\n");
    for (Item item : items) {
      recursivePrint(item, "");
    }
  }

  /**
   * Goes through all tree in order to print every item inside it
   *
   * @param item Projects or tasks in items tree.
   * @param tabs Tab to line.
   */
  public void recursivePrint(Item item, String tabs) {
    if (item instanceof Task) {
      print(tabs + item.getFormattedTable());
    } else {
      print(tabs + "-" + item.getFormattedTable());
      tabs = tabs + "   "; // leaves some space in order to see in which item level are we
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
