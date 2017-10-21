
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import model.Printer;
import model.Project;
import model.Task;

public class Main {
	
	static Project project;
	static Project subProject;
	
	static Task task1;
	static Task task2;
	static Task task3;
		
	public Main(){
	}
	
    public static void main( String[] args ) {
    	
    	//In first place, we set all the items
    	setTree();
    	
    	new Printer(project);

    	
    	project.startTask(1);
    	//System.out.print("task 3 started\n");
    	sleep(3000);
    
    	project.stopTask(1);
    	//System.out.print("task 3 stopped\n\n\n");
    	sleep(3000);
    	
    	project.startTask(1);
    	//System.out.print("task3 started\n");
    	sleep(3000);
    	
    	//project.stopTask(1);
    	//System.out.print("task 3 stopped\n\n\n");

    	
    	/*((Project) project.getItems().get(0)).startTask(0);
    	((Project) project.getItems().get(0)).startTask(1);
    	sleep(2000);
    	System.out.print("sleep 2000\n");
    	((Project) project.getItems().get(0)).stopTask(0);
    	((Project) project.getItems().get(0)).stopTask(1);*/
    	
       
    }
    
    public static void setTree(){
        project = new Project("P1", "first project from node 0", null);
        project.newProject("P2", "subproject of P1");
        project.newTask("T3", "task 3 in project P1");
        
        ((Project) project.getItems().get(0)).newTask("T1","sub sub task1 from P2");
        ((Project) project.getItems().get(0)).newTask("T2","sub sub task2 from P2");
    }
    
    public static void sleep(int millis){
    	try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
