package net.minecraft;

import java.util.ArrayList;
import net.minecraft.Util$OS;

public class MinecraftLauncher
{
    private static final int MIN_HEAP = 511;
    private static final int RECOMMENDED_HEAP = 1024;

    public static void main(String[] var0) throws Exception
    {
        float var1 = (float)(Runtime.getRuntime().maxMemory() / 1024L / 1024L);

        /* RAM mod >>> */
        if ( var1 > 511.0F && LauncherOptions.get("Java-RAM", "").isEmpty() )
        /* <<< RAM mod */
        {
            LauncherFrame.main(var0);
        }
        else
        {
            try
            {
                String var2 = MinecraftLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                ArrayList var3 = new ArrayList();

                if (Util.getPlatform().equals(Util$OS.WINDOWS))
                {
                    var3.add("javaw");
                }
                else
                {
                    var3.add("java");
                }

                /* RAM mod >>> */
                String maxMemory = LauncherOptions.get("Java-RAM", "");
                if( maxMemory.isEmpty() )
                	var3.add("-Xmx1024m");
                else
                	var3.add("-Xmx" + maxMemory + "m");
                /* <<< RAM mod */
                var3.add("-Dsun.java2d.noddraw=true");
                var3.add("-Dsun.java2d.d3d=false");
                var3.add("-Dsun.java2d.opengl=false");
                var3.add("-Dsun.java2d.pmoffscreen=false");
                var3.add("-classpath");
                var3.add(var2);
                var3.add("net.minecraft.LauncherFrame");
                ProcessBuilder var4 = new ProcessBuilder(var3);
                Process var5 = var4.start();

                if (var5 == null)
                {
                    throw new Exception("!");
                }

                System.exit(0);
            }
            catch (Exception var6)
            {
                var6.printStackTrace();
                LauncherFrame.main(var0);
            }
        }
    }
}
