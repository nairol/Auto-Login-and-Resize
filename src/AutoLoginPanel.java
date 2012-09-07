package net.minecraft;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;


public class AutoLoginPanel extends LoginForm
{
    private static final long serialVersionUID = 1L;
    private TexturedPanel texturedPanel;
    private TransparentLabel infoLabel;
    private LauncherFrame launcherFrame;
    
    public static int retryCount = 3;
    
    public static boolean abortLogin = false;
    
    public AutoLoginPanel( LauncherFrame launcherFrame ) {
        super( launcherFrame );
        this.launcherFrame = launcherFrame;
        removeAll();
        
        texturedPanel = new TexturedPanel();
        texturedPanel.setLayout( new BorderLayout() );
        texturedPanel.add(new LogoPanel(), "North");
        infoLabel = new TransparentLabel("");
        infoLabel.setHorizontalAlignment(TransparentLabel.CENTER);
        
        texturedPanel.add( infoLabel, "Center" );
        texturedPanel.setPreferredSize( new Dimension(100, 100) );

        add(texturedPanel, "Center");
        
        addKeyListener( new EscKeyListener() );
        setFocusable(true);
        doLogin();
    }
    
    public void setLoggingIn()
    {
        infoLabel.setText("For options press Esc.");
    }
    
    public void setError(String s)
    {
    	if( s.equals("Login failed") && retryCount > 0 )
    	{
    		infoLabel.setText("Login failed! Retrying...");
    	}
    	else
    	{
    		retryCount = -1;
    		super.setError(s);
    	}
    }
    
    public void setNoNetwork()
    {
    	if( retryCount > 0 )
    	{
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException e) { }
			retryCount--;
			doLogin();
    	}
    	else
    	{
	        setPreferredSize( new Dimension(854, 480));
	        super.setNoNetwork(false);
    	}
    }
    
    private class EscKeyListener implements KeyListener
    {
        public void keyPressed(KeyEvent key)
        {
            if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
            {
                abortLogin = true;
                infoLabel.setText("Switching to default launcher...");
            }
        }

        public void keyReleased(KeyEvent key) { }

        public void keyTyped(KeyEvent key) { }
        
    }
    
    public static boolean isAutoLoginEnabled()
    {
    	return Boolean.parseBoolean( LauncherOptions.get("AutoLogin-Enabled", "false") );
    }
    
    public static void setAutoLoginEnabled( boolean enabled )
    {
    	LauncherOptions.set( "AutoLogin-Enabled", Boolean.toString( enabled ) );
    }
}