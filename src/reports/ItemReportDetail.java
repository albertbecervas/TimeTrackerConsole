package reports;

/**
 * 
 * @invariant name != null
 * @invariant duration != null
 * @invariant initialDate != null
 * @invariant finalDate != null
 * @version 2.00, 29/11/2017
 */
public class ItemReportDetail {

  String name;
  String duration;
  String initialDate;
  String finalDate;

  int secondsForHour = 3600;
  int secondsForMinut = 60;

  /**
   * Set item details for create a report.
   * @param name Name of item.
   * @param duration Duration of item.
   * @param initialDate Initial date of item.
   * @param finalDate Final date of item.
   * @throws IllegalArgumentException Invalid argument.
   */
  public ItemReportDetail(String name, long duration, String initialDate, String finalDate)
      throws IllegalArgumentException {
    if (name == null || initialDate == null || finalDate == null) {
      throw new IllegalArgumentException("Name or date can't be null."); 
    }
    this.name = name;
    this.initialDate = initialDate;
    this.finalDate = finalDate;

    final long hours = duration / secondsForHour;
    final long minuts = (duration - hours * secondsForHour) / secondsForMinut;
    final long seconds = duration - secondsForHour * hours - secondsForMinut * minuts;
    this.duration = String.valueOf(hours + "h " + minuts + "m " + seconds + "s");
  }

  public String getIncludedDuration() {
    assert duration != null : "duration is null";
    return duration;
  }

  public String getName() {
    assert name != null : "name is null";
    return name;
  }

  public String getInitialDate() {
    assert initialDate != null : "initialDate is null";
    return initialDate;
  }

  public String getFinalDate() {
    assert finalDate != null : "finalDate is null";
    return finalDate;
  }
}
