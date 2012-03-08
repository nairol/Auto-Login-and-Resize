// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft;

import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;

// Referenced classes of package net.minecraft:
//            LauncherFrame

public class MinecraftLauncher
{

    public MinecraftLauncher()
    {
    }

    public static void main(String args[])
        throws Exception
    {
        float f = Runtime.getRuntime().maxMemory() / 1024L / 1024L;
        if(f > 511F)
        {
            LauncherFrame.main(args);
        } else
        {
            try
            {
                String s = (net.minecraft.MinecraftLauncher.class).getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                ArrayList arraylist = new ArrayList();
                arraylist.add("javaw");
                /* Auto login >>> */
                //arraylist.add("-Xmx1024m");
                arraylist.add("-Xmx" + LauncherOptions.get("Java-RAM", "1024") + "m");
                /* <<< Auto login */
                arraylist.add("-Dsun.java2d.noddraw=true");
                arraylist.add("-Dsun.java2d.d3d=false");
                arraylist.add("-Dsun.java2d.opengl=false");
                arraylist.add("-Dsun.java2d.pmoffscreen=false");
                arraylist.add("-classpath");
                arraylist.add(s);
                arraylist.add("net.minecraft.LauncherFrame");
                ProcessBuilder processbuilder = new ProcessBuilder(arraylist);
                Process process = processbuilder.start();
                if(process == null)
                {
                    throw new Exception("!");
                }
                System.exit(0);
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
                LauncherFrame.main(args);
            }
        }
    }

    private static final int MIN_HEAP = 511;
    private static final int RECOMMENDED_HEAP = 1024;
}
