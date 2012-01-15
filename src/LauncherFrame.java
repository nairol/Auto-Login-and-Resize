// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.UIManager;

// Referenced classes of package net.minecraft:
//            LoginForm, Launcher, Util

public class LauncherFrame extends Frame
{

    public LauncherFrame()
    {
        super("Minecraft Launcher");
        customParameters = new HashMap();
        setBackground(Color.BLACK);
        loginForm = new LoginForm(this);
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        /* Auto login >>> */
        if( AutoLoginPanel.isAutoLoginEnabled() )
        {
            loginForm = new AutoLoginPanel(this);
            jpanel.setPreferredSize(new Dimension(300, 120));
        }
        else
        {
            loginForm = new LoginForm(this);
            jpanel.setPreferredSize(new Dimension(854, 480));
        }
        jpanel.add(loginForm, "Center");
        /* <<< Auto login */
        setLayout(new BorderLayout());
        add(jpanel, "Center");
        pack();
        setLocationRelativeTo(null);
        try
        {
            setIconImage(ImageIO.read((net.minecraft.LauncherFrame.class).getResource("favicon.png")));
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent)
            {
                (new Thread() {

                    public void run()
                    {
                        try
                        {
                            Thread.sleep(30000L);
                        }
                        catch(InterruptedException interruptedexception)
                        {
                            interruptedexception.printStackTrace();
                        }
                        System.out.println("FORCING EXIT!");
                        System.exit(0);
                    }

                }
).start();
                if(launcher != null)
                {
                    launcher.stop();
                    launcher.destroy();
                }
                System.exit(0);
            }

        }
);
        /* Auto Resize >>> */
        addComponentListener(new ComponentAdapter() {
        	public void componentResized(ComponentEvent e)
        	{
        		AutoResize.resized(LauncherFrame.this);
        	}
        });
        /* <<< Auto Resize */
    }

    public void playCached(String s)
    {
        try
        {
            if(s == null || s.length() <= 0)
            {
                s = "Player";
            }
            launcher = new Launcher();
            launcher.customParameters.putAll(customParameters);
            launcher.customParameters.put("userName", s);
            launcher.init();
            removeAll();
            add(launcher, "Center");
            validate();
            /* AutoResize >>> */
            AutoResize.prepareForLaunch(this);
            /* <<< AutoResize */
            launcher.start();
            loginForm = null;
            setTitle("Minecraft");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            showError(exception.toString());
        }
    }

    public void login(String s, String s1)
    {
        try
        {
            String s2 = (new StringBuilder("user=")).append(URLEncoder.encode(s, "UTF-8")).append("&password=").append(URLEncoder.encode(s1, "UTF-8")).append("&version=").append(13).toString();
            String s3 = Util.excutePost("https://login.minecraft.net/", s2);
            if(s3 == null)
            {
                showError("Can't connect to minecraft.net");
                loginForm.setNoNetwork();
                return;
            }
            if(!s3.contains(":"))
            {
                if(s3.trim().equals("Bad login"))
                {
                    showError("Login failed");
                } else
                if(s3.trim().equals("Old version"))
                {
                    loginForm.setOutdated();
                    showError("Outdated launcher");
                } else
                {
                    showError(s3);
                }
                loginForm.setNoNetwork();
                return;
            }
            String as[] = s3.split(":");
            /* Auto login >>> */
            if( AutoLoginPanel.abortLogin ) // If the user pressed Esc in the AutoLoginPanel ...
            {
                removeAll();
                showError(""); // ... show the normal launcher ...
                return;        // ... and don't continue here.
            }
            /* <<< Auto login */
            launcher = new Launcher();
            launcher.customParameters.putAll(customParameters);
            launcher.customParameters.put("userName", as[2].trim());
            launcher.customParameters.put("latestVersion", as[0].trim());
            launcher.customParameters.put("downloadTicket", as[1].trim());
            launcher.customParameters.put("sessionId", as[3].trim());
            launcher.init();
            removeAll();
            add(launcher, "Center");
            validate();
            /* AutoResize >>> */
            AutoResize.prepareForLaunch(this);
            /* <<< AutoResize */
            launcher.start();
            loginForm.loginOk();
            loginForm = null;
            setTitle("Minecraft");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            showError(exception.toString());
            loginForm.setNoNetwork();
        }
    }

    private void showError(String s)
    {
        /* Auto login >>> */
    	if( s.equals("Login failed") && AutoLoginPanel.retryCount > 0 )
    	{
    		loginForm.setError(s);
    		return;
    	}
        removeAll();
        AutoLoginPanel.abortLogin = false;
        if( loginForm instanceof AutoLoginPanel )
            loginForm = new LoginForm(this);
        add(loginForm);
        if( s.isEmpty() == false )
            loginForm.setError(s);
        validate();
        loginForm.setPreferredSize( new Dimension(854, 480) );
        pack();
        setLocationRelativeTo(null);
        /* <<< Auto login */
    }

    public boolean canPlayOffline(String s)
    {
        Launcher launcher1 = new Launcher();
        launcher1.customParameters.putAll(customParameters);
        launcher1.init(s, null, null, null);
        return launcher1.canPlayOffline();
    }

    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception exception) { }
        LauncherFrame launcherframe = new LauncherFrame();
        launcherframe.setVisible(true);
        launcherframe.customParameters.put("stand-alone", "true");
        if(args.length >= 3)
        {
            String s = args[2];
            String s1 = "25565";
            if(s.contains(":"))
            {
                String args1[] = s.split(":");
                s = args1[0];
                s1 = args1[1];
            }
            launcherframe.customParameters.put("server", s);
            launcherframe.customParameters.put("port", s1);
        }
        if(args.length >= 1)
        {
            launcherframe.loginForm.userName.setText(args[0]);
            if(args.length >= 2)
            {
                launcherframe.loginForm.password.setText(args[1]);
                launcherframe.loginForm.doLogin();
            }
        }
    }

    public static final int VERSION = 13;
    private static final long serialVersionUID = 1L;
    public Map customParameters;
    public Launcher launcher;
    public LoginForm loginForm;
}
