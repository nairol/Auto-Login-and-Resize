package net.minecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class LauncherOptions {

    private static final String configFile = "launcher-options.txt";
    
	public static void set( String key, String value )
	{
        Properties prop = new Properties();
        File confFile = new File(Util.getWorkingDirectory(), configFile);
        
        try {
            prop.load( new FileReader( confFile ) );
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }
        
        prop.setProperty(key, value);
        
        try {
            prop.store( new FileWriter( confFile ), "");
        } catch (IOException e) { } // I don't care atm.
	}
	
	public static String get( String key, String defaultValue )
	{
        Properties prop = new Properties();
        File confFile = new File(Util.getWorkingDirectory(), configFile);
        
        try {
            prop.load( new FileReader( confFile ) );
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }
        
        return prop.getProperty(key, defaultValue);
	}
	
}
