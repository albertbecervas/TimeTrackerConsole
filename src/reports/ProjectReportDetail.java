package reports;

import model.Project;

public class ProjectReportDetail {

	Project project;
	String duration;
	String initialDate;
	String finalDate;
	
    int secondsPerHour = 3600;
    int secondsPerMinute = 60;
	
	public ProjectReportDetail(Project project, long duration, String initial, String finalDate){
		this.project = project;
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
	
	public Project getProject(){
		return project;
	}
	
	public String getInitialDate(){
		return initialDate;
	}
	
	public String getFinalDate(){
		return finalDate;
	}
}
