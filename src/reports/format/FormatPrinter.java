package com.omitsis.lvp.timer.reports.format;

import com.omitsis.lvp.timer.reports.ItemReportDetail;
import com.omitsis.lvp.timer.reports.TaskReportDetail;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FormatPrinter {

    ArrayList<ItemReportDetail> projects;
    ArrayList<ItemReportDetail> subProjects;
    ArrayList<ItemReportDetail> tasks;

    public FormatPrinter(ArrayList<ItemReportDetail> projects, ArrayList<ItemReportDetail> subProjects, ArrayList<ItemReportDetail> tasks) {
        this.projects = projects;
        this.subProjects = subProjects;
        this.tasks = tasks;
        printTableDetailed();
    }

    public FormatPrinter(ArrayList<ItemReportDetail> projects){
        this.projects = projects;
        printTableDetailed();
    }

    public void printTableDetailed() {
        PrintWriter writer;

        String header = String.format("%s %20s %20s %20s \r\n", "Projecte", "Data inici", "Data final", " Temps total");
        String separator = "__________________________________________________________\n";
        ArrayList<String> table = new ArrayList<String>();

        table.add(header);
        table.add(separator);

        for (ItemReportDetail project : projects) {
            String projectReport = project.getName();
            projectReport += " - ";
            projectReport += project.getInitialDate();
            projectReport += " - ";
            projectReport += project.getFinalDate();
            projectReport += " - ";
            projectReport += project.getIncludedDuration();
            projectReport += "\n";

            table.add(projectReport);
        }

        header = String.format("%s %20s %20s %20s \r\n", "subProject", "Data inici", "Data final", " Temps total");
        table.add("\n" + header);
        table.add(separator);

        for (ItemReportDetail project : subProjects) {
            String projectReport = project.getName();
            projectReport += " - ";
            projectReport += project.getInitialDate();
            projectReport += " - ";
            projectReport += project.getFinalDate();
            projectReport += " - ";
            projectReport += project.getIncludedDuration();
            projectReport += "\n";

            table.add(projectReport);
        }

        header = String.format("%s %20s %20s %20s \r\n", "Task", "Data inici", "Data final", " Temps total");
        table.add("\n" + header);
        table.add(separator);

        for (ItemReportDetail task : tasks) {
            String projectReport = task.getName();
            projectReport += " - ";
            projectReport += task.getInitialDate();
            projectReport += " - ";
            projectReport += task.getFinalDate();
            projectReport += " - ";
            projectReport += task.getIncludedDuration();
            projectReport += "\n";

            table.add(projectReport);
        }

        try {
            writer = new PrintWriter("DetailedReport.txt", "UTF-8");
            for (String row : table) {
                writer.print(row);
                print(row);
            }

            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void print(String text) {
        System.out.print(text);
    }


}
