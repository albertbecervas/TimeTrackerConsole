package format;

import reports.Report;

/*
 * This class will be called when a report is set and needs to be extracted it to a file
 * in an specific format.
 * @author Albert
 * @version 2.00, 29/11/2017
 */
public abstract class Format {

  public abstract void generateFile(Report report);
}
