package net.minecraft;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

class OptionsPanel$4 implements ActionListener
{
    final OptionsPanel this$0;

    OptionsPanel$4(OptionsPanel var1)
    {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent var1)
    {
    	/* Auto login >>> */
    	AutoLoginPanel.setAutoLoginEnabled( this$0.autoLogin.isSelected() );
    	/* <<< Auto login */
    	/* Auto Resize >>> */
    	AutoResize.setDefaultSize( Integer.parseInt( this$0.widthField.getText() ), Integer.parseInt( this$0.heightField.getText() ) );
    	AutoResize.setDefaultMaximized( this$0.stateMaximized.isSelected() );
    	AutoResize.setDefaultFullscreen( this$0.stateFullscreen.isSelected() );
    	if( this$0.stateFullscreen.isSelected() )
    		javax.swing.JOptionPane.showMessageDialog(this$0, "You have selected fullscreen mode.\nThis window mode has some limitations at the moment because of a bug in Minecraft.\nYou will not be able to leave it with F11, for example.", "Fullscreen mode", JOptionPane.INFORMATION_MESSAGE);
    	/* <<< Auto Resize */
        /* RAM Mod >>> */
    	if( this$0.ramField.getText().equals( LauncherOptions.get("Java-RAM", "1024") ) == false )
    		javax.swing.JOptionPane.showMessageDialog(this$0, "You have changed the maximum allocated memory.\nPlease restart the launcher for this setting to take effect.", "Max. memory allocation changed", JOptionPane.INFORMATION_MESSAGE);
        LauncherOptions.set("Java-RAM", this$0.ramField.getText() );
        /* <<< RAM Mod */
        this.this$0.setVisible(false);
    }
}
