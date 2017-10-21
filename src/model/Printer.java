package model;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import observable.Clock;

public class Printer implements Observer{
	
	Project project;
	
	String tabs = "";
	
	public Printer(Project project){
		Clock.getInstance().addObserver(this);
		this.project = project;
	}
	
    public void printTable() throws IOException{
    	//System.out.flush();
    	Runtime.getRuntime().exec("clear");
    	print("\r \n\n TIME TABLE:\n");
    	recursive(project,"");
    }
    
    public void recursive(Item item, String tabs){
    	if(item instanceof Task){
    		print(tabs + item.getFormattedTable());
    	} else {
    		print(tabs + item.getFormattedTable());
    		tabs = tabs + "   -";
    		for(Item subItem : ((Project) item).getItems()){
    			recursive(subItem,tabs);
    		}
    	}
    }
    
    public void print(String text){
    	System.out.print(text);
    }

	@Override
	public void update(Observable o, Object arg) {
		try {
			printTable();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
