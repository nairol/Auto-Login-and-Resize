/* Auto Resize >>> */
package net.minecraft;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LauncherFrame$2 extends ComponentAdapter
{
    final LauncherFrame this$0;

    LauncherFrame$2(LauncherFrame var1)
    {
        this.this$0 = var1;
    }
    
	public void componentResized(ComponentEvent e)
	{
		AutoResize.resized(this$0);
	}
}
/* <<< Auto Resize */