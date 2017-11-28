package reports;

import model.Task;

public class TaskReportDetail {
	Task task;
	String duration;
	String initialDate;
	String finalDate;
	
    int secondsPerHour = 3600;
    int secondsPerMinute = 60;
	
	public TaskReportDetail(Task task, long duration, String initial, String finalDate){
		this.task = task;
		this.initialDate = initial;
		this.finalDate = finalDate;
		
        final long hours = duration / secondsPerHour;
        final long minuts = (duration - hours * secondsPerHour) / secondsPerMinute;
        final long seconds = duration - secondsPerHour * hours - secondsPerMinute * minuts;
        String formattedDuration = String.valueOf(hours + "h " + minuts + "m " + seconds + "s");
        this.duration = formattedDuration;
	}
	
	public String getIncludedDuration(){
		return duration;
	}
	
	public Task getTask(){
		return task;
	}
	
	public String getInitialDate(){
		return initialDate;
	}
	
	public String getFinalDate(){
		return finalDate;
	}
	
}
