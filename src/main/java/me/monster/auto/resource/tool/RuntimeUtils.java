package me.monster.auto.resource.tool;

public class RuntimeUtils {

    public static boolean isDebug() {
        String os = System.getProperty("os.name", "");
        String user = System.getProperty("user.name", "");
        return "Mac OS X".equals(os) && ("imac".equals(user) || "jiangjiwei".equals(user));
    }
}
