package model;

public class Item{

    protected String name;
    protected String description;
    protected String databasePath;
    protected String itemType;
    
    protected boolean isOpen;
    protected long duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    public long getDuration(){
    	return duration;
    }
    
    public void setDuration(long duration){
    	this.duration = duration;
    }
    
    public void addDuration(long duration){
    	this.duration += duration;
    }
    
    public boolean isOpen(){
    	return isOpen;
    }
    
    public void setOpen(boolean isOpen){
    	this.isOpen = isOpen;
    }
    
    public String getFormattedTable(){
        int segonsPerHora = 3600;
        int segonsPerMinut = 60;
       
        final long hores = duration / segonsPerHora;
        final long minuts = (duration - hores * segonsPerHora) / segonsPerMinut;
        final long segons = duration - segonsPerHora * hores - segonsPerMinut * minuts;
        
        return String.valueOf(getName() + "----->" + "duration = " +String.valueOf(hores + "h " + minuts + "m " + segons + "s") + "\n");
    }
}
