package liquibase.util;

public class SystemUtil {

    public static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows ");
    }

}
