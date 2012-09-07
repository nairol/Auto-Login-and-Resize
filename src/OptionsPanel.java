package net.minecraft;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class OptionsPanel extends JDialog
{
    private static final long serialVersionUID = 1L;
    
    /* Auto login >>> */
    public final JCheckBox autoLogin;
    /* <<< Auto login */
    /* Auto Resize >>> */
    public final JTextField widthField;
    public final JTextField heightField;
    public final JRadioButton stateMaximized;
    public final JRadioButton stateFullscreen;
    /* <<< Auto Resize */
    /* RAM Mod >>> */
    public final JTextField ramField;
    /* <<< RAM Mod */

    public OptionsPanel(Frame var1)
    {
        super(var1);
        this.setModal(true);
        JPanel var2 = new JPanel(new BorderLayout());
        JLabel var3 = new JLabel("Launcher options", 0);
        var3.setBorder(new EmptyBorder(0, 0, 16, 0));
        var3.setFont(new Font("Default", 1, 16));
        var2.add(var3, "North");
        JPanel var4 = new JPanel(new BorderLayout());
        JPanel var5 = new JPanel(new GridLayout(0, 1));
        JPanel var6 = new JPanel(new GridLayout(0, 1));
        var4.add(var5, "West");
        var4.add(var6, "Center");
        JButton var7 = new JButton("Force update!");
        var7.addActionListener(new OptionsPanel$1(this, var7));
        var5.add(new JLabel("Force game update: ", 4));
        var6.add(var7);
        var5.add(new JLabel("Game location on disk: ", 4));
        /* Auto Resize (let me fix that for you Notch) >>> */
        TransparentLabel var8 = new TransparentLabel( "<html><u>" + Util.getWorkingDirectory().toString() + "</u></html>" );
        /* <<< Auto Resize */
        var8.setCursor(Cursor.getPredefinedCursor(12));
        var8.addMouseListener(new OptionsPanel$3(this));
        var8.setForeground(new Color(2105599));
        var6.add(var8);
        var2.add(var4, "Center");
        /* Auto login >>> */
        autoLogin = new JCheckBox("Automatic login");
        var5.add(autoLogin);
        var6.add(new JLabel("(To cancel it press Esc while the game loads)"));
        autoLogin.setSelected( AutoLoginPanel.isAutoLoginEnabled() );
        /* <<< Auto login */
        /* Auto Resize >>> */
        var5.add(new JLabel("Default video size: ", 4));
        JPanel autoSizePanel = new JPanel();
        Dimension d = AutoResize.getDefaultSize();
        widthField = new JTextField( "" + d.width, 4 );
        heightField = new JTextField( "" + d.height, 4 );
        autoSizePanel.add(widthField, "West");
        autoSizePanel.add(new JLabel("x"), "West");
        autoSizePanel.add(heightField, "West");
        JButton useCurrentSizeButton = new JButton("Use current size");
        useCurrentSizeButton.addActionListener(new OptionsPanel$5(this));
        autoSizePanel.add(useCurrentSizeButton, "East");
        var6.add(autoSizePanel);
        var5.add(new JLabel("Window mode: ", 4));
        JRadioButton stateNormal = new JRadioButton("normal");
        stateMaximized = new JRadioButton("maximized");
        stateFullscreen = new JRadioButton("fullscreen");
        JPanel autoStatePanel = new JPanel();
        autoStatePanel.add(stateNormal, "West");
        autoStatePanel.add(stateMaximized, "West");
        autoStatePanel.add(stateFullscreen, "West");
        var6.add(autoStatePanel);
        ButtonGroup autoStateGroup = new ButtonGroup();
        autoStateGroup.add(stateNormal);
        autoStateGroup.add(stateMaximized);
        autoStateGroup.add(stateFullscreen);
        if( AutoResize.getDefaultFullscreen() )
        	stateFullscreen.setSelected(true);
        else if( AutoResize.getDefaultMaximized() )
        	stateMaximized.setSelected(true);
        else
        	stateNormal.setSelected(true);
        /* <<< Auto Resize */
        /* RAM Mod >>> */
        JPanel ramPanel = new JPanel();
        ramField = new JTextField( LauncherOptions.get("Java-RAM", ""), 4 );
        ramPanel.add(ramField, "West");
        ramPanel.add(new JLabel("Megabytes   (Default: 1024)"), "Center");
        var5.add(new JLabel("Max. allocated memory:", 4));
        var6.add(ramPanel);
        /* <<< RAM Mod */
        JPanel var9 = new JPanel(new BorderLayout());
        var9.add(new JPanel(), "Center");
        JButton var10 = new JButton("Done");
        var10.addActionListener(new OptionsPanel$4(this));
        var9.add(var10, "East");
        var9.setBorder(new EmptyBorder(16, 0, 0, 0));
        var2.add(var9, "South");
        this.add(var2);
        var2.setBorder(new EmptyBorder(16, 24, 24, 24));
        this.pack();
        this.setLocationRelativeTo(var1);
    }
}
