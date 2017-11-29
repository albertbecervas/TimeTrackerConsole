package reports;
/**
 * 
 * @invariant name != null;
 * @invariant duration != null;
 * @invariant initialDate != null;
 * @invariant finalDate != null;
 */
public class ItemReportDetail {

    String name;
    String duration;
    String initialDate;
    String finalDate;

    int secondsForHour = 3600;
    int secondsForMinut = 60;

    public ItemReportDetail(String name, long duration, String initialDate, String finalDate) throws IllegalArgumentException{
    	if(name == null || initialDate == null || finalDate == null) 
    		throw new IllegalArgumentException("Neither name nor initialDate nor initialDate can be null");
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
