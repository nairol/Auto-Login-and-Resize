/* Auto Resize >>> */
package net.minecraft;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class OptionsPanel$5 implements ActionListener
{
    final OptionsPanel this$0;

    OptionsPanel$5(OptionsPanel var1)
    {
        this.this$0 = var1;
    }
	
	public void actionPerformed(ActionEvent e)
	{
		Dimension d = AutoResize.getCurrentSize();
		this$0.widthField.setText( "" + d.width );
		this$0.heightField.setText( "" + d.height );
	}

}
/* <<< Auto Resize */