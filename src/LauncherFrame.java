package net.minecraft;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class LauncherFrame extends Frame
{
    public static final int VERSION = 13;
    private static final long serialVersionUID = 1L;
    public Map customParameters = new HashMap();
    public Launcher launcher;
    public LoginForm loginForm;

    public LauncherFrame()
    {
        super("Minecraft Launcher");
        this.setBackground(Color.BLACK);
        this.loginForm = new LoginForm(this);
        JPanel var1 = new JPanel();
        var1.setLayout(new BorderLayout());
        
        /* Auto login >>> */
        if( AutoLoginPanel.isAutoLoginEnabled() )
        {
            loginForm = new AutoLoginPanel(this);
            var1.setPreferredSize(new Dimension(300, 120));
        }
        else
        {
            loginForm = new LoginForm(this);
            var1.setPreferredSize(new Dimension(854, 480));
        }
        var1.add(loginForm, "Center");
        /* <<< Auto login */
        
        this.setLayout(new BorderLayout());
        this.add(var1, "Center");
        this.pack();
        this.setLocationRelativeTo((Component)null);

        try
        {
            this.setIconImage(ImageIO.read(LauncherFrame.class.getResource("favicon.png")));
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }

        this.addWindowListener(new LauncherFrame$1(this));
        /* Auto Resize >>> */
        this.addComponentListener(new LauncherFrame$2(this));
        /* <<< Auto Resize */
    }

    public void playCached(String var1, boolean var2)
    {
        try
        {
            if (var1 == null || var1.length() <= 0)
            {
                var1 = "Player";
            }

            this.launcher = new Launcher();
            this.launcher.customParameters.putAll(this.customParameters);
            this.launcher.customParameters.put("userName", var1);
            this.launcher.customParameters.put("demo", "" + var2);
            this.launcher.customParameters.put("sessionId", "1");
            this.launcher.init();
            this.removeAll();
            this.add(this.launcher, "Center");
            this.validate();
            /* AutoResize >>> */
            AutoResize.prepareForLaunch(this);
            /* <<< AutoResize */
            this.launcher.start();
            this.loginForm = null;
            this.setTitle("Minecraft");
        }
        catch (Exception var4)
        {
            var4.printStackTrace();
            this.showError(var4.toString());
        }
    }

    public void login(String var1, String var2)
    {
        try
        {
            HashMap var3 = new HashMap();
            var3.put("user", var1);
            var3.put("password", var2);
            var3.put("version", Integer.valueOf(13));
            String var4 = Util.executePost("https://login.minecraft.net/", (Map)var3);

            if (var4 == null)
            {
                this.showError("Can\'t connect to minecraft.net");
                this.loginForm.setNoNetwork(false);
                return;
            }

            if (!var4.contains(":"))
            {
                boolean var7 = false;

                if (var4.trim().equals("Bad login"))
                {
                    this.showError("Login failed");
                }
                else if (var4.trim().equals("Old version"))
                {
                    this.loginForm.setOutdated();
                    this.showError("Outdated launcher");
                }
                else if (var4.trim().equals("User not premium"))
                {
                    this.showError(var4);
                    var7 = true;
                }
                else
                {
                    this.showError(var4);
                }

                this.loginForm.setNoNetwork(var7);
                return;
            }

            String[] var5 = var4.split(":");
            /* Auto login >>> */
            if( AutoLoginPanel.abortLogin ) // If the user pressed Esc in the AutoLoginPanel ...
            {
                removeAll();
                showError(""); // ... show the normal launcher ...
                return;        // ... and don't continue here.
            }
            /* <<< Auto login */
            this.launcher = new Launcher();
            this.launcher.customParameters.putAll(this.customParameters);
            this.launcher.customParameters.put("userName", var5[2].trim());
            this.launcher.customParameters.put("latestVersion", var5[0].trim());
            this.launcher.customParameters.put("downloadTicket", var5[1].trim());
            this.launcher.customParameters.put("sessionId", var5[3].trim());
            this.launcher.init();
            this.removeAll();
            this.add(this.launcher, "Center");
            this.validate();
            /* AutoResize >>> */
            AutoResize.prepareForLaunch(this);
            /* <<< AutoResize */
            this.launcher.start();
            this.loginForm.loginOk();
            this.loginForm = null;
            this.setTitle("Minecraft");
        }
        catch (Exception var6)
        {
            var6.printStackTrace();
            this.showError(var6.toString());
            this.loginForm.setNoNetwork(false);
        }
    }

    private void showError(String var1)
    {
        /* Auto login >>> */
    	if( var1.equals("Login failed") && AutoLoginPanel.retryCount > 0 )
    	{
    		loginForm.setError(var1);
    		return;
    	}
        removeAll();
        AutoLoginPanel.abortLogin = false;
        if( loginForm instanceof AutoLoginPanel )
            loginForm = new LoginForm(this);
        add(loginForm);
        if( var1.isEmpty() == false )
            loginForm.setError(var1);
        validate();
        loginForm.setPreferredSize( new Dimension(854, 480) );
        pack();
        setLocationRelativeTo(null);
        /* <<< Auto login */
    }

    public boolean canPlayOffline(String var1)
    {
        Launcher var2 = new Launcher();
        var2.customParameters.putAll(this.customParameters);
        var2.init(var1, (String)null, (String)null, "1");
        return var2.canPlayOffline();
    }

    public static void main(String[] var0)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception var8)
        {
            ;
        }

        System.out.println("asdf");
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("java.net.preferIPv6Addresses", "false");
        LauncherFrame var1 = new LauncherFrame();
        var1.setVisible(true);
        var1.customParameters.put("stand-alone", "true");
        String var2 = null;
        String var3 = null;
        String[] var4 = var0;
        int var5 = var0.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            String var7 = var4[var6];

            if (!var7.startsWith("-u=") && !var7.startsWith("--user="))
            {
                if (!var7.startsWith("-p=") && !var7.startsWith("--password="))
                {
                    if (var7.startsWith("--noupdate"))
                    {
                        var1.customParameters.put("noupdate", "true");
                    }
                }
                else
                {
                    var3 = getArgValue(var7);
                    var1.customParameters.put("password", var3);
                    var1.loginForm.password.setText(var3);
                }
            }
            else
            {
                var2 = getArgValue(var7);
                var1.customParameters.put("username", var2);
                var1.loginForm.userName.setText(var2);
            }
        }

        if (var0.length >= 3)
        {
            String var9 = var0[2];
            String var10 = "25565";

            if (var9.contains(":"))
            {
                String[] var11 = var9.split(":");
                var9 = var11[0];
                var10 = var11[1];
            }

            var1.customParameters.put("server", var9);
            var1.customParameters.put("port", var10);
        }
    }

    private static String getArgValue(String var0)
    {
        int var1 = var0.indexOf(61);
        return var1 < 0 ? "" : var0.substring(var1 + 1);
    }
}
