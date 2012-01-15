package net.minecraft;

import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AutoResize {
	
    private static int resetTitleCounter = Integer.MIN_VALUE;
    private static Thread resetTitleThread;
    private static LauncherFrame lf;
	
	public static void setDefaultSize( int width, int height )
	{
		if( width <= 0 || height <= 0 )
			return;
		LauncherOptions.set( "AutoResize-Width", Integer.toString(width) );
		LauncherOptions.set( "AutoResize-Height", Integer.toString(height) );
	}
	
	public static Dimension getCurrentSize()
	{
		if( lf != null && lf.loginForm != null )
		{
			return lf.loginForm.getSize();
		}
		
		if( lf != null && lf.launcher != null )
		{
			return lf.launcher.getSize();
		}
		return new Dimension(1,1);
	}
	
	public static Dimension getDefaultSize()
	{
		Dimension d = new Dimension();
		d.width = Integer.parseInt( LauncherOptions.get("AutoResize-Width", "854") );
		d.height = Integer.parseInt( LauncherOptions.get("AutoResize-Height", "480") );
		return d;
	}
	
	public static void setDefaultMaximized( boolean maximized )
	{
		LauncherOptions.set( "AutoResize-Maximized", Boolean.toString(maximized) );
	}
	
	public static boolean getDefaultMaximized()
	{
        return Boolean.parseBoolean( LauncherOptions.get("AutoResize-Maximized", "false") );
	}
	
	public static void setDefaultFullscreen( boolean fullscreen )
	{
		LauncherOptions.set( "AutoResize-Fullscreen", Boolean.toString(fullscreen) );
	}
	
	public static boolean getDefaultFullscreen()
	{
		return Boolean.parseBoolean( LauncherOptions.get("AutoResize-Fullscreen", "false") );
	}
	
	public static void resized( final LauncherFrame lf )
	{
		if( resetTitleCounter == Integer.MIN_VALUE)
		{
			resetTitleCounter = 0;
			AutoResize.lf = lf;
			return;
		}
		Dimension d;
		if( lf.loginForm != null )
		{
			d = lf.loginForm.getSize();
    		lf.setTitle("New size: " + d.width + "x" + d.height);
		}
		else if( lf.launcher != null )
		{
			d = lf.launcher.getSize();
    		lf.setTitle("New size: " + d.width + "x" + d.height);
		}
		resetTitleCounter = 4;
		if( resetTitleThread == null )
		{
			resetTitleThread = new Thread(){
    	    	public void run() {
    	    		while(resetTitleCounter > 0)
    	    		{
    	    			try {
    						Thread.sleep(500);
    					}
    	    			catch (InterruptedException e) {}
    	    			resetTitleCounter--;
    	    		}
            		if( lf.launcher == null )
            		{
                		lf.setTitle("Minecraft Launcher");
            		}
            		else
            		{
                		lf.setTitle("Minecraft");
            		}
    	    		resetTitleThread = null;
    	    	}
	    	};
	    	resetTitleThread.start();
		}	
	}
	
	public static void prepareForLaunch( LauncherFrame lf )
	{
        lf.launcher.setPreferredSize( AutoResize.getDefaultSize() );
        lf.pack();
        lf.setLocationRelativeTo( null );
        if( getDefaultMaximized() )
        	lf.setExtendedState(Frame.MAXIMIZED_BOTH);
        
        if( getDefaultFullscreen() )
        {
        	// The following lines are a hack. They set the window in a "soft fullscreen" mode that cannot be left by F11.
        	// It should be done correctly by passing the parameter "fullscreen" with value "true" the applet.
        	// Minecraft unfortunately has a bug that makes it disregard that parameter. :(
        	lf.dispose();
        	lf.setUndecorated(true);
        	lf.setExtendedState(Frame.MAXIMIZED_BOTH);
        	lf.setVisible(true);
        }
	}
}
