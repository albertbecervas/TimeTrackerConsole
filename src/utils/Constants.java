package utils;

import ch.qos.logback.classic.Level;

public abstract class Constants {
	//Level of the logger
	public static final Level LOGGER_LEVEL = Level. WARN;
	
    //Menu constants
    public static final int RUN_TEST_1 = 1;
    public static final int RUN_TEST_2 = 2;
    public static final int RESET_TREE = 3;
    public static final int CREATE_PROJECT = 4;
    public static final int CREATE_TASK = 5;
    
    //Clock's unit in seconds
    public static final int CLOCK_SECONDS = 2;

}
