package reports;

import elements.Paragraph;
import elements.Separator;
import elements.Title;
import format.HtmlFormatPrinter;
import format.TextFormatPrinter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Interval;
import model.Item;
import model.Project;
import model.Task;

/**
 * Generates a Brief report of the main items that shows: Duration, initial
 * date, final date and name of every item
 * @author Albert
 * @version 2.00, 29/11/2017
 */
public class BriefReport extends Report {

  public ArrayList<ItemReportDetail> mainItems;

  private long duration = 0;

  private DateFormat df;

  /**
   * Set parameters for create a Brief Report.
   * @param items Items for create a report.
   * @param format Format of report.
   */
  public BriefReport(ArrayList<Item> items, String format) 
      throws IllegalArgumentException, NullPointerException {
    if (items == null) {
      throw new NullPointerException("Items is null.");
    }
    this.items = items;

    mainItems = new ArrayList<ItemReportDetail>();

    this.df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    setFormat(format);

    generateBriefReport();
  }

  /*
   * Sets the format of the file that will be generated
   */
  private void setFormat(String format) throws IllegalArgumentException {
    if (format != "txt" && format != "html") {
      throw new IllegalArgumentException("Invalid value for format.");
    }
    switch (format) {
      case "txt":
        this.format = new TextFormatPrinter();
        break;
      case "html":
        this.format = new HtmlFormatPrinter();
        break;
      default:
        assert false : "Invalid format.";
    }
  }

  /**
   * Generate a Brief Report.
   */
  public void generateBriefReport() {
    for (Item item : items) {
      // Calculates all item details and saves them in the projects lists.
      try {
        setItemDetails(item);
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
    }

    setReportElements();
    assert format != null : "Format is null.";
    format.generateFile(this);
  }

  /**
   * Sets a list of items with all calculated details.
   * @param item Project or task that we want to calculate the details.
   */
  private void setItemDetails(Item item) throws NullPointerException {
    recursiveTreeSearch(item);
    Date initialDate = calculateInitialDate(item);
    Date endDate = calculateFinalDate(item);

    String initialDateText;
    String endDateText;
    if (initialDate != null && endDate != null) {
      initialDateText = df.format(initialDate);
      endDateText = df.format(endDate);
    } else {
      initialDateText = "-";
      endDateText = "-";
    }

    // Add this to the node projects lists.
    mainItems.add(new ItemReportDetail(item.getName(), duration / 1000,
        initialDateText, endDateText));

    duration = 0; // Reset the global variable in order to start again with the other item.
  }

  /**
   * Iterates through the items in order to calculate every subItem parameters.
   * @param item Project or task that we want to calculate the details.
   * @return long The accumulate duration of all items inside.
   */
  private long recursiveTreeSearch(Item item) throws NullPointerException {
    long itemDuration = 0;
    if (item == null) {
      throw new NullPointerException("Item is null.");
    }
    if (item instanceof Task) { // Basic case
      for (Interval interval : ((Task) item).getIntervals()) {
        try {
          itemDuration = setIntervalDetails(item, itemDuration, interval);
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        }

      }

      duration += itemDuration;// update the projects time

    } else {
      for (Item subItem : ((Project) item).getItems()) {
        recursiveTreeSearch(subItem); // recursive function
      }
    }
    return duration;
  }

  /**
   * Calculates the duration of every interval.
   *
   * @param item Task that we want to calculate the details of the interval.
   * @param itemDuration Duration of the task to return in the iterative function.
   * @param interval Interval that we want to set details.
   * @return itemDuration Duration of item.
   */
  private long setIntervalDetails(Item item, long itemDuration, Interval interval) 
      throws NullPointerException, IllegalArgumentException {
    if (item == null || interval == null) {
      throw new NullPointerException("Neither item nor interval can be null.");
    }
    if (itemDuration < 0) {
      throw new IllegalArgumentException("itemDuration can't be smaller than 0.");
    }
    long intervalDuration = 0;
    Date initialDate = calculateInitialDate(interval);
    Date endDate = calculateFinalDate(interval);

    if (initialDate != null && endDate != null) {
      intervalDuration = endDate.getTime() - initialDate.getTime();
      itemDuration += intervalDuration;
    } else {
      intervalDuration = 0;
    }
    return itemDuration;
  }

  private Date calculateInitialDate(Item item) throws NullPointerException {
    if (item == null) {
      throw new NullPointerException("Item is null.");
    }
    long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
    long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();

    if (initialDateTime <= startPeriodTime) {
      if (endDateTime <= startPeriodTime) {
        return null;
      }
      return startPeriodDate;
    } else if (initialDateTime > startPeriodTime && initialDateTime <= endPeriodTime) {
      return item.getPeriod().getStartWorkingDate();
    } else {
      return null;
    }
  }

  private Date calculateInitialDate(Interval interval) throws NullPointerException {
    if (interval == null) {
      throw new NullPointerException("Interval is null.");
    }
    long initialDateTime = interval.getInitialDate().getTime();
    long endDateTime = interval.getFinalDate().getTime();

    if (initialDateTime <= startPeriodTime) {
      if (endDateTime <= startPeriodTime) {
        return null;
      }
      return startPeriodDate;
    } else if (initialDateTime > startPeriodTime && initialDateTime <= endPeriodTime) {
      return interval.getInitialDate();
    } else {
      return null;
    }
  }

  private Date calculateFinalDate(Item item) throws NullPointerException {
    if (item == null) {
      throw new NullPointerException("Item is null.");
    }
    long initialDateTime = item.getPeriod().getStartWorkingDate().getTime();
    long endDateTime = item.getPeriod().getFinalWorkingDate().getTime();

    if (endDateTime >= endPeriodTime) {
      if (initialDateTime >= endPeriodTime) {
        return null;
      }
      return endPeriodDate;
    } else if (endDateTime < endPeriodTime && endDateTime >= startPeriodTime) {
      return item.getPeriod().getFinalWorkingDate();
    } else {
      return null;
    }
  }

  private Date calculateFinalDate(Interval interval) throws NullPointerException {
    if (interval == null) {
      throw new NullPointerException("Interval is null.");
    }
    long initialDateTime = interval.getInitialDate().getTime();
    long endDateTime = interval.getFinalDate().getTime();

    if (endDateTime >= endPeriodTime) {
      if (initialDateTime >= endPeriodTime) {
        return null;
      }
      return endPeriodDate;
    } else if (endDateTime < endPeriodTime && endDateTime >= startPeriodTime) {
      return interval.getFinalDate();
    } else {
      return null;
    }
  }

  /**
   * Once all items are calculated, we set the elements that we want our report to have.
   */
  private void setReportElements() {

    elements.add(new Separator());
    elements.add(new Title("INFORME BREU"));
    elements.add(new Separator());
    elements.add(new Paragraph("desde-->  " + this.startDateString));
    elements.add(new Paragraph("fins-->  " + this.endDateString));
    elements.add(new Paragraph("desde-->  " + this.generationDate));
    elements.add(new Separator());

    elements.add(new Title("Projects   Data_Inici    Data_final      Durada"));
    elements.add(new Separator());
    for (ItemReportDetail item : mainItems) {
      String paragraph = item.getName() + "  |  " + item.getInitialDate() + "  |  "
          + item.getFinalDate() + "  |  " + item.getIncludedDuration();
      elements.add(new Paragraph(paragraph));
    }
  }
}
