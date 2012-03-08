// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

// Referenced classes of package net.minecraft:
//            Util, TransparentLabel, GameUpdater

public class OptionsPanel extends JDialog
{

    public OptionsPanel(Frame frame)
    {
        super(frame);
        setModal(true);
        JPanel jpanel = new JPanel(new BorderLayout());
        JLabel jlabel = new JLabel("Launcher options", 0);
        jlabel.setBorder(new EmptyBorder(0, 0, 16, 0));
        jlabel.setFont(new Font("Default", 1, 16));
        jpanel.add(jlabel, "North");
        JPanel jpanel1 = new JPanel(new BorderLayout());
        JPanel jpanel2 = new JPanel(new GridLayout(0, 1));
        JPanel jpanel3 = new JPanel(new GridLayout(0, 1));
        jpanel1.add(jpanel2, "West");
        jpanel1.add(jpanel3, "Center");
        final JButton forceButton = new JButton("Force update!");
        forceButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                GameUpdater.forceUpdate = true;
                forceButton.setText("Will force!");
                forceButton.setEnabled(false);
            }

        }
);
        jpanel2.add(new JLabel("Force game update: ", 4));
        jpanel3.add(forceButton);
        jpanel2.add(new JLabel("Game location on disk: ", 4));
        /* Auto Resize (let me fix that for you Notch) >>> */
        TransparentLabel transparentlabel = new TransparentLabel("<html><u>" + Util.getWorkingDirectory().toString() + "</u></html>");
        /* <<< Auto Resize */
        transparentlabel.setCursor(Cursor.getPredefinedCursor(12));
        transparentlabel.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent mouseevent)
            {
                try
                {
                    Util.openLink((new URL((new StringBuilder("file://")).append(Util.getWorkingDirectory().getAbsolutePath()).toString())).toURI());
                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            }

        }
);
        transparentlabel.setForeground(new Color(0x2020ff));
        jpanel3.add(transparentlabel);
        jpanel.add(jpanel1, "Center");
        /* Auto login >>> */
        final JCheckBox autoLogin = new JCheckBox("Automatic login");
        jpanel2.add(autoLogin);
        jpanel3.add(new JLabel("(To cancel it press Esc while the game loads)"));
        autoLogin.setSelected( AutoLoginPanel.isAutoLoginEnabled() );
        /* <<< Auto login */
        /* Auto Resize >>> */
        jpanel2.add(new JLabel("Default video size: ", 4));
        JPanel autoSizePanel = new JPanel();
        Dimension d = AutoResize.getDefaultSize();
        final JTextField widthField = new JTextField( "" + d.width, 4 );
        final JTextField heightField = new JTextField( "" + d.height, 4 );
        autoSizePanel.add(widthField, "West");
        autoSizePanel.add(new JLabel("x"), "West");
        autoSizePanel.add(heightField, "West");
        JButton useCurrentSizeButton = new JButton("Use current size");
        useCurrentSizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension d = AutoResize.getCurrentSize();
				widthField.setText( "" + d.width );
				heightField.setText( "" + d.height );
			}
		});
        autoSizePanel.add(useCurrentSizeButton, "East");
        jpanel3.add(autoSizePanel);
        jpanel2.add(new JLabel("Window mode: ", 4));
        JRadioButton stateNormal = new JRadioButton("normal");
        final JRadioButton stateMaximized = new JRadioButton("maximized");
        final JRadioButton stateFullscreen = new JRadioButton("fullscreen");
        JPanel autoStatePanel = new JPanel();
        autoStatePanel.add(stateNormal, "West");
        autoStatePanel.add(stateMaximized, "West");
        autoStatePanel.add(stateFullscreen, "West");
        jpanel3.add(autoStatePanel);
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
        final JTextField ramField = new JTextField( LauncherOptions.get("Java-RAM", "1024"), 4 );
        ramPanel.add(ramField, "West");
        ramPanel.add(new JLabel("Megabytes   (Default: 1024)"), "Center");
        jpanel2.add(new JLabel("Max. allocated memory:", 4));
        jpanel3.add(ramPanel);
        /* <<< RAM Mod */
        JPanel jpanel4 = new JPanel(new BorderLayout());
        jpanel4.add(new JPanel(), "Center");
        JButton jbutton = new JButton("Done");
        jbutton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
            	/* Auto login >>> */
            	AutoLoginPanel.setAutoLoginEnabled( autoLogin.isSelected() );
            	/* <<< Auto login */
            	/* Auto Resize >>> */
            	AutoResize.setDefaultSize( Integer.parseInt( widthField.getText() ), Integer.parseInt( heightField.getText() ) );
            	AutoResize.setDefaultMaximized( stateMaximized.isSelected() );
            	AutoResize.setDefaultFullscreen( stateFullscreen.isSelected() );
            	if( stateFullscreen.isSelected() )
            		javax.swing.JOptionPane.showMessageDialog(OptionsPanel.this, "You have selected fullscreen mode.\nThis window mode has some limitations at the moment because of a bug in Minecraft.\nYou will not be able to leave it with F11, for example.", "Fullscreen mode", JOptionPane.INFORMATION_MESSAGE);
            	/* <<< Auto Resize */
                /* RAM Mod >>> */
            	if( ramField.getText().equals( LauncherOptions.get("Java-RAM", "1024") ) == false )
            		javax.swing.JOptionPane.showMessageDialog(OptionsPanel.this, "You have changed the maximum allocated memory.\nPlease restart the launcher for this setting to take effect.", "Max. memory allocation changed", JOptionPane.INFORMATION_MESSAGE);
                LauncherOptions.set("Java-RAM", ramField.getText() );
                /* <<< RAM Mod */
                setVisible(false);
            }

        }
);
        jpanel4.add(jbutton, "East");
        jpanel4.setBorder(new EmptyBorder(16, 0, 0, 0));
        jpanel.add(jpanel4, "South");
        add(jpanel);
        jpanel.setBorder(new EmptyBorder(16, 24, 24, 24));
        pack();
        setLocationRelativeTo(frame);
    }

    private static final long serialVersionUID = 1L;
}
